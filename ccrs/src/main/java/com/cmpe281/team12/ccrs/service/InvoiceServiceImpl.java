package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Invoice;
import com.cmpe281.team12.ccrs.model.InvoiceStatus;
import com.cmpe281.team12.ccrs.model.RobotUsage;
import com.cmpe281.team12.ccrs.model.payload.CreateInvoiceRequest;
import com.cmpe281.team12.ccrs.model.payload.GenerateInvoiceRequest;
import com.cmpe281.team12.ccrs.model.payload.PayInvoiceRequest;
import com.cmpe281.team12.ccrs.repository.InvoiceRepository;
import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LogManager.getLogger(InvoiceServiceImpl.class);

    // Using this as the default US Dollar rate per active hour of robot usage.
    private static final double DEFAULT_ROBOT_USD_RATE = 1.19;

    private InvoiceRepository invoiceRepository;
    private RobotUsageService robotUsageService;

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Autowired
    public void setRobotUsageService(RobotUsageService robotUsageService) {
        this.robotUsageService = robotUsageService;
    }

    @Override
    public List<Invoice> getInvoices(String accountId) {
        logger.info("InvoiceServiceImpl::getInvoices() -- accountId: " + accountId);
        return invoiceRepository.findByAccountId(accountId);
    }

    @Override
    public List<Invoice> getInvoicesByIssueDate(String accountId, Date startDate, Date endDate) {
        logger.info("InvoiceServiceImpl::getInvoicesByIssueDate() -- accountId: " + accountId +
                ", startDate: " + startDate + ", endDate: " + endDate);
        return invoiceRepository.findByAccountIdAndIssuedAtBetween(accountId, startDate, endDate);
    }

    @Override
    public List<Invoice> getInvoicesByDueDate(String accountId, Date startDate, Date endDate) {
        logger.info("InvoiceServiceImpl::getInvoicesByDueDate() -- accountId: " + accountId +
                ", startDate: " + startDate + ", endDate: " + endDate);
        return invoiceRepository.findByAccountIdAndDueDateBetween(accountId, startDate, endDate);
    }

    @Override
    public Invoice createInvoice(CreateInvoiceRequest request) {
        logger.info("InvoiceServiceImpl::createInvoice() -- request: " + request);
        Invoice invoice = new Invoice(request);
        return invoiceRepository.save(invoice);
    }

    private boolean isWithinDateRange(Date trackDate, Date startDate, Date endDate) {
        if (trackDate.after(startDate) && trackDate.before(endDate))
            return true;
        return false;
    }

    private Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        return Date.valueOf(utilDate
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
    }

    @Override
    public Invoice generateInvoice(GenerateInvoiceRequest request) {

        logger.info("InvoiceServiceImpl::generateInvoice() -- request: " + request);
        double amountDue;

        // TODO: pull all usage records for the timeframe for this account, then calculate amount due into invoice
        Map<String, RobotUsage> usageData = robotUsageService.getAccountRobotUsage(request.getAccountId());
        if (usageData == null || usageData.isEmpty()) {
            // No robot usage found for this account, so no invoice can be generated.
            logger.warn("InvoiceServiceImpl::generateInvoice() -- no robot usage found.");
            return null;
        }

        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();

        // TODO: What if there is already an existing invoice for this time period?
        //  Need to check first before generating a new invoice.

        List<RobotUsage> usageList = usageData.values()
                .stream()
                .filter(u -> isWithinDateRange(convertUtilDateToSqlDate(u.getTrackDate()), startDate, endDate))
                .collect(Collectors.toList());
        if (usageList.isEmpty()) {
            // No robot usage within the start/end date required, so no invoice can be generated.
            logger.warn("InvoiceServiceImpl::generateInvoice() -- no robot usage satisfying start/end date specified.");
            return null;
        }

        // Get the total number of active hours for all robots for this account (within the start/end date).
        int numHoursActive = usageList
                .stream()
                .map(usage -> Math.abs(usage.getEndActiveTime() - usage.getStartActiveTime()))
                .mapToInt(Integer::intValue).sum();
        amountDue = numHoursActive * DEFAULT_ROBOT_USD_RATE;
        logger.info("InvoiceServiceImpl::generateInvoice() -- numHoursActive: " + numHoursActive + ", amountDue: " + amountDue);

        // Determine due date by evaluating the
        // end date of the generate invoice request.
        LocalDate localDueDate = endDate.toLocalDate().plusMonths(1);
        Date dueDate = new Date(localDueDate.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000);

        // Create a new invoice record to store into the db.
        Invoice invoice = new Invoice();
        invoice.setAccountId(request.getAccountId());
        invoice.setAmountDue(amountDue);
        invoice.setStatus(InvoiceStatus.OPEN.name());
        invoice.setIssuedAt(new Date(System.currentTimeMillis()));
        invoice.setDueDate(dueDate);

        logger.info("InvoiceServiceImpl::generateInvoice() -- invoice: " + invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice payInvoice(PayInvoiceRequest request) throws Exception {

        logger.info("InvoiceServiceImpl::payInvoice() -- request: " + request);
        Double paidAmount = request.getPaidAmount();
        Preconditions.checkArgument(paidAmount > 0, "invalid paid amount");

        Invoice invoice = invoiceRepository.findByInvoiceIdAndAccountId(request.getInvoiceId(), request.getAccountId());
        if (invoice == null) {
            throw new Exception("No existing invoice found for invoiceId: " + request.getInvoiceId()
                    + ", accountId: " + request.getAccountId());
        }

        // calculate new balance after payment
        Double existingBalance = invoice.getAmountDue();
        Double newBalance = existingBalance - paidAmount;
        logger.info("InvoiceServiceImpl::payInvoice() -- newBalance: " + newBalance);

        // if zero balance, update invoice status
        if (newBalance <= 0) {
            newBalance = 0.0;
            invoice.setStatus(InvoiceStatus.PAID.name());
        }
        invoice.setAmountDue(newBalance);

        // update the db record
        logger.info("InvoiceServiceImpl::payInvoice() -- *** DONE ***");
        return invoiceRepository.save(invoice);
    }
}
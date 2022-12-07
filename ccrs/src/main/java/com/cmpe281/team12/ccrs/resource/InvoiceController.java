package com.cmpe281.team12.ccrs.resource;

import com.cmpe281.team12.ccrs.model.Invoice;
import com.cmpe281.team12.ccrs.model.payload.CreateInvoiceRequest;
import com.cmpe281.team12.ccrs.model.payload.GenerateInvoiceRequest;
import com.cmpe281.team12.ccrs.model.payload.PayInvoiceRequest;
import com.cmpe281.team12.ccrs.service.InvoiceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger logger = LogManager.getLogger(InvoiceController.class);

    private InvoiceService invoiceService;

    @Autowired
    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

//    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches all invoices for a given account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invoice> getInvoices(@ApiParam(value = "Existing account ID to look up", required = true) @RequestParam String accountId) {
        return invoiceService.getInvoices(accountId);
    }

//    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/byissuedate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches invoices by issue date time period for a given account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invoice> getInvoicesByIssueDate(@ApiParam(value = "Existing account ID to look up", required = true) @RequestParam String accountId,
                                           @ApiParam(value = "Issue start date to include", required = true) @RequestParam Date startDate,
                                           @ApiParam(value = "Issue end date to include", required = true) @RequestParam Date endDate) {
        return invoiceService.getInvoicesByIssueDate(accountId, startDate, endDate);
    }

//    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/byduedate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches invoices by due date time period for a given account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invoice> getInvoicesByDueDate(@ApiParam(value = "Existing account ID to look up", required = true) @RequestParam String accountId,
                                           @ApiParam(value = "Due start date to include", required = true) @RequestParam Date startDate,
                                           @ApiParam(value = "Due end date to include", required = true) @RequestParam Date endDate) {
        return invoiceService.getInvoicesByDueDate(accountId, startDate, endDate);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/")
    @ApiOperation(value = "Creates a new invoice by specifying an amount due and due date",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice createInvoice(@ApiParam(value = "Create Invoice Spec", required = true)
                                 @RequestBody CreateInvoiceRequest request) {
        logger.info("InvoiceController::createInvoice() -- request: " + request);
        return invoiceService.createInvoice(request);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/generate")
    @ApiOperation(value = "Generates a new invoice for a specified time frame",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice generateInvoice(@ApiParam(value = "Generate Invoice Spec", required = true)
                                 @RequestBody GenerateInvoiceRequest request) {
        logger.info("InvoiceController::generateInvoice() -- request: " + request);
        return invoiceService.generateInvoice(request);
    }

    //    @PreAuthorize("hasRole('BUSINESS')")
    @PostMapping(value = "/pay")
    @ApiOperation(value = "Pay invoice",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice payInvoice(@ApiParam(value = "Pay Invoice Spec", required = true)
                                 @RequestBody PayInvoiceRequest request) throws Exception {
        logger.info("InvoiceController::payInvoice() -- request: " + request);
        return invoiceService.payInvoice(request);
    }
}
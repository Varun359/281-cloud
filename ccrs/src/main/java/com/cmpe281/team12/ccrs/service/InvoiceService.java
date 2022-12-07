package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Invoice;
import com.cmpe281.team12.ccrs.model.payload.CreateInvoiceRequest;
import com.cmpe281.team12.ccrs.model.payload.GenerateInvoiceRequest;
import com.cmpe281.team12.ccrs.model.payload.PayInvoiceRequest;

import java.sql.Date;
import java.util.List;

public interface InvoiceService {
    List<Invoice> getInvoices(String accountId);
    List<Invoice> getInvoicesByIssueDate(String accountId, Date startDate, Date endDate);
    List<Invoice> getInvoicesByDueDate(String accountId, Date startDate, Date endDate);
    Invoice createInvoice(CreateInvoiceRequest request);
    Invoice generateInvoice(GenerateInvoiceRequest request);
    Invoice payInvoice(PayInvoiceRequest request) throws Exception;
}

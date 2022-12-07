package com.cmpe281.team12.ccrs.repository;

import com.cmpe281.team12.ccrs.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByAccountId(String accountId);

    Invoice findByInvoiceIdAndAccountId(Long invoiceId, String accountId);

    @Query(value="SELECT * FROM invoice as i WHERE i.account_id = ?1 AND i.issued_at >= ?2 AND i.issued_at <= ?3", nativeQuery = true)
    List<Invoice> findByAccountIdAndIssuedAtBetween(String accountId, Date startDate, Date endDate);

    @Query(value="SELECT * FROM invoice as i WHERE i.account_id = ?1 AND i.due_date >= ?2 AND i.due_date <= ?3", nativeQuery = true)
    List<Invoice> findByAccountIdAndDueDateBetween(String accountId, Date startDate, Date endDate);
}

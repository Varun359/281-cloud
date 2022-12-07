import React, { Component } from "react";
import "./Invoice.css";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import { Bar } from "react-chartjs-2";
import axios from "axios";
import { Chart, registerables } from "chart.js";
Chart.register(...registerables);

const { API_URL } = require("../../../config/config").default;

class Invoice extends Component {
  searchInvoiceAPI = "/invoice/byduedate";
  payInvoiceSuffix = "/invoice/pay";

  constructor(props) {
    super(props);
    this.state = {
      startDate: "",
      endDate: "",
      isInvoice: false,
      invoice_list: [],
      invoiceInfo: {},
      selectedInvoiceId: "",
      selectedPaidAmount: 0,
      key: [],
      value: [],
      isPay: false,
    };
  }

  handleStartDateChanged = (e) => {
    console.log(e.target.value);
    this.setState({
      startDate: e.target.value,
    });
    console.log(this.state.startDate);
  };
  handleEndDateChanged = (e) => {
    console.log(e.target.value);
    this.setState({
      endDate: e.target.value,
    });
  };

  handleSelectInvoice = (e) => {
    console.log("handleSelectInvoice");
    console.log(e.target.value);
    console.log(e.target.type);
    console.log(this.state.invoice_list);

    this.result = this.state.invoice_list.find(
      (invoice) => invoice.invoiceId === e.target.value
    );
    this.setState({
      selectedInvoiceId: e.target.value,
      selectedPaidAmount: this.result.amountDue,
    });
  };

  handleSearchInvoice = (e) => {
    e.preventDefault();
    const { startDate, endDate } = this.state;
    console.log("handleSearchInvoice()");

    var requestUrl =
      API_URL +
      this.searchInvoiceAPI +
      "?accountId=" +
      localStorage.getItem("localAccountId") +
      "&endDate=" +
      endDate +
      "&startDate=" +
      startDate;

    console.log(requestUrl);

    var token = localStorage.getItem("apiAuthToken");
    axios
      .get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log("handleSearchInvoice() SUCCESS");
        this.setState({
          invoice_list: response.data,
        });
        this.state.key = [];
        this.state.value = [];
        response.data.map((obj) => {
          if (obj.status == "OPEN") {
            this.state.key.push(obj.invoiceId);
            this.state.value.push(obj.amountDue);
          }
        });
        console.log(this.state.key);
        console.log(this.state.value);
        this.state.invoiceInfo = {
          labels: this.state.key,
          datasets: [
            {
              label: "invoices",
              data: this.state.value,
              backgroundColor: [
                "rgba(255, 99, 132, 0.2)",
                "rgba(255, 159, 64, 0.2)",
                "rgba(255, 205, 86, 0.2)",
                "rgba(75, 192, 192, 0.2)",
                "rgba(54, 162, 235, 0.2)",
                "rgba(153, 102, 255, 0.2)",
                "rgba(201, 203, 207, 0.2)",
              ],
              borderColor: [
                "rgb(255, 99, 132)",
                "rgb(255, 159, 64)",
                "rgb(255, 205, 86)",
                "rgb(75, 192, 192)",
                "rgb(54, 162, 235)",
                "rgb(153, 102, 255)",
                "rgb(201, 203, 207)",
              ],
              borderWidth: 1,
              barPercentage: 0.5,
            },
          ],
        };
        this.setState({
          isInvoice: true,
        });
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("There was an error: " + this.state.errorMessage);
      });
  };

  handlePayInvoice = (e) => {
    e.preventDefault();
    console.log("handlePayInvoice()");
    var requestUrl = API_URL + this.payInvoiceSuffix;
    var token = localStorage.getItem("apiAuthToken");
    var account = localStorage.getItem("localAccountId");

    console.log("account ", account);
    console.log(this.state.selectedInvoiceId, this.state.selectedPaidAmount);
    axios
      .post(
        requestUrl,
        {
          invoiceId: this.state.selectedInvoiceId,
          accountId: account,
          paidAmount: this.state.selectedPaidAmount,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        console.log("handlePayInvoice() SUCCESS");
        this.setState({
          isPay: true,
        });
        window.location.reload(true);
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("There was an error: " + this.state.errorMessage);
      });
  };

  render() {
    console.log("Invoice render()");

    const date = new Date();
    const futureDate = date.getDate() + 3;
    date.setDate(futureDate);
    var defaultDate = date.toLocaleDateString("en-CA");

    return (
      <div>
        <div>
          <h1>
            <center>Invoice </center>
          </h1>
        </div>

        <div style={{ textAlign: "center" }}>
          <form onSubmit={this.handleSearchInvoice}>
            <label htmlFor="start">
              <b>Start Due Date</b>
            </label>
            <input
              type="date"
              onChange={this.handleStartDateChanged}
              defaultValue={defaultDate}
            />
            &nbsp;&nbsp;
            <label htmlFor="start">
              <span>
                <b>End Due Date</b>
              </span>
            </label>
            <input
              type="date"
              onChange={this.handleEndDateChanged}
              defaultValue={defaultDate}
            />
            <button className="btn" type="submit">
              Search
            </button>
          </form>
        </div>

        {this.state.isInvoice ? (
          <div>
            <div className="bar__graph">
              <div className="invoice__bar">
                <Bar data={this.state.invoiceInfo} />
              </div>
              <div className="invoice__table">
                <table id="invoice_list_table">
                  <thead>
                    <tr>
                      <th padding="default">
                        <b>Invoice ID</b>
                      </th>
                      <th align="right">
                        <b>Issued At</b>
                      </th>
                      <th align="right">
                        <b>Due Date</b>
                      </th>
                      <th align="right">
                        <b>Amount</b>
                      </th>
                      <th align="right">
                        <b>Status</b>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {console.log(this.state.invoice_list)}
                    {this.state.invoice_list.map((invoice) => [
                      <tr key={invoice.invoiceId}>
                        <td padding="default">{invoice.invoiceId}</td>
                        <td padding="default">{invoice.issuedAt}</td>
                        <td padding="default">{invoice.dueDate}</td>
                        <td padding="default">{invoice.amountDue}</td>
                        <td padding="default">{invoice.status}</td>
                      </tr>,
                    ])}
                  </tbody>
                </table>
              </div>
            </div>

            <div style={{ textAlign: "center" }}>
              <label> Invoice ID </label>
              <input type="text" onChange={this.handleSelectInvoice} />
              <button className="btn" onClick={this.handlePayInvoice}>
                <span>Pay</span>
              </button>
            </div>
          </div>
        ) : (
          <p />
        )}
      </div>
    );
  }
}

export default Invoice;

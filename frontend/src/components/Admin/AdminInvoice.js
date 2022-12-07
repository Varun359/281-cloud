import React, { Component } from "react";
import axios from "axios";
import "./AdminInvoice.css";

const { API_URL } = require("../../config/config").default;

class AdminInvoice extends Component {
  getAccountsSuffix = "/account/all";
  requestInvoiceSuffix = "/invoice/generate";
  getAllInvoiceSuffix = "/invoice/all";

  constructor(props) {
    super(props);
    this.state = {
      selectedStartDate: "",
      selectedEndDate: "",
      business_list: [],
      selectedBusinessId: "",
      selectedBusinessAddress: "",
      selectedBusiness: false,
      selectedBusinessName: false,
      invoice_list: [],
      isRent: "",
    };
  }
  componentDidMount() {
    console.log("componentDidMount()");
    var token = localStorage.getItem("apiAuthToken");
    //get all accounts:
    axios
      .get(API_URL + this.getAccountsSuffix, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        this.setState({ business_list: response.data });
      })
      .catch((error) => {
        console.log("ERROR Business Name List", error.message);
      });
  }

  handleRentAccount = (e) => {
    this.setState({
      isRent: e.target.value,
    });
  };

  handleBusinessChange = (e) => {
    e.preventDefault();
    if (e.target.value === "") return;

    console.log("handleBusinessChange()");
    this.result = this.state.business_list.find(
      (business) => business.businessName === e.target.value
    );
    console.log("account id ", this.result.accountId);
    this.setState({
      selectedBusinessName: e.target.value,
      selectedBusinessId: this.result.accountId,
      selectedBusinessAddress:
        this.result.address +
        ", " +
        this.result.city +
        ", " +
        this.result.state +
        ", " +
        this.result.zip,
      selectedBusiness: true,
    });
    console.log(this.state.selectedBusinessId);
    this.getAllInvoices(this.result.accountId);
  };

  handleStartDateChange = (e) => {
    e.preventDefault();
    this.setState({
      selectedStartDate: e.target.value,
    });
    console.log(e.target.value);
  };

  handleEndDateChange = (e) => {
    e.preventDefault();
    this.setState({
      selectedEndDate: e.target.value,
    });
    console.log(e.target.value);
  };

  requestLeaseInvoice = (e) => {
    console.log("requestInvoice()");
    var requestUrl = API_URL + this.requestInvoiceSuffix;
    var token = localStorage.getItem("apiAuthToken");

    axios
      .post(
        requestUrl,
        {
          isRent: this.state.isRent,
          accountId: this.state.selectedBusinessId,
          startDate: this.state.selectedStartDate,
          endDate: this.state.selectedEndDate,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        console.log("requestInvoice SUCCESS");

        this.getAllInvoices(this.state.selectedBusinessId);
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("ERROR requestInvoice" + this.state.errorMessage);
      });
  };

  requestInvoice = (e) => {
    console.log("requestInvoice()");
    var requestUrl = API_URL + this.requestInvoiceSuffix;
    var token = localStorage.getItem("apiAuthToken");

    axios
      .post(
        requestUrl,
        {
          isRent: this.state.isRent,
          accountId: this.state.selectedBusinessId,
          startDate: this.state.selectedStartDate,
          endDate: this.state.selectedEndDate,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        console.log("requestInvoice SUCCESS");

        this.getAllInvoices(this.state.selectedBusinessId);
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("ERROR requestInvoice" + this.state.errorMessage);
      });
  };

  /*getAllInvoices() {
        console.log("getAllInvoices()");
        var account = this.state.selectedBusinessId;
        console.log(account);
        var token = localStorage.getItem('apiAuthToken');
        var requestUrl = API_URL + this.getAllInvoiceSuffix+ "?accountId=" + account;
        console.log("requestUrl ", requestUrl);

        //get all invoices at account Id:
        axios.get(requestUrl,
            {
                headers: {
                'Authorization': `Bearer ${token}`
                }
            }
          ).then(response=> {
            console.log("getAllInvoices() SUCCESS");
            console.log(response.data);            
            this.setState({invoice_list: response.data});
          }).catch(error=> {
            console.log("ERROR Business Name List", error.message);
          });
    }*/

  getAllInvoices(id) {
    console.log("getAllInvoices(id)");
    var token = localStorage.getItem("apiAuthToken");
    var requestUrl = API_URL + this.getAllInvoiceSuffix + "?accountId=" + id;
    console.log("requestUrl ", requestUrl);

    //get all invoices at account Id:
    axios
      .get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log("getAllInvoices() SUCCESS");
        console.log(response.data);
        this.setState({ invoice_list: response.data });
      })
      .catch((error) => {
        console.log("ERROR Business Name List", error.message);
      });
  }

  render() {
    return (
      <div>
        <div>
          <h1>
            <center>Admin Invoice Management</center>
          </h1>
        </div>
        <div className="form-group_account">
          <label htmlFor="password">
            {/* Do you want to generate invoice for rent: */}
          </label>
        </div>
        <center>
          <div className="radio" style={{ width: "15%" }}>
            <label style={{ fontSize: "16px" }}>
              <input
                type="radio"
                value="Yes"
                style={{ height: "15px", width: "15px" }}
                checked={this.state.isRent === "Yes"}
                onChange={this.handleRentAccount}
              />
              <b>Rent Invoice</b>
            </label>
          </div>
          <div className="radio" style={{ width: "15%" }}>
            <label style={{ fontSize: "16px" }}>
              <input
                type="radio"
                value="No"
                style={{ height: "15px", width: "15px" }}
                checked={this.state.isRent === "No"}
                onChange={this.handleRentAccount}
              />
              <b>Lease Invoice</b>
            </label>
          </div>
        </center>
        {/* --------------------- */}
        {this.state.isRent === "" ? (
          <></>
        ) : (
          <>
            <div style={{ textAlign: "center" }}>
              <label className="order__label">Business List:</label>
              <input
                className="order__input"
                list="location"
                onChange={this.handleBusinessChange}
              />
              <datalist id="location">
                {this.state.business_list ? (
                  this.state.business_list.map((business) => [
                    <option
                      key={business.accountId}
                      value={business.businessName}
                    />,
                  ])
                ) : (
                  <option value="None available" />
                )}
              </datalist>
            </div>
            <div style={{ textAlign: "center" }}>
              {this.state.selectedBusiness ? (
                <div
                  style={{
                    marginLeft: "3%",
                    textAlign: "center",
                    color: "#f1641e",
                  }}
                >
                  <h3>
                    <b
                      style={{
                        color: "black",
                      }}
                    >
                      Business Name:
                    </b>
                    {this.state.selectedBusinessName}
                  </h3>
                  <br />
                  <b
                    style={{
                      color: "black",
                    }}
                  >
                    Business ID:
                  </b>
                  <span>{this.state.selectedBusinessId}</span>
                  <br />
                  <b
                    style={{
                      color: "black",
                    }}
                  >
                    Business Address:
                  </b>
                  {this.state.selectedBusinessAddress}
                  <br />
                </div>
              ) : (
                <div></div>
              )}
            </div>
            <div style={{ textAlign: "center" }}>
              <label htmlFor="start">Start Date</label>
              <input type="date" onChange={this.handleStartDateChange} />
              <label htmlFor="start"> End Date</label>
              <input type="date" onChange={this.handleEndDateChange} />
              {this.state.isRent === "Yes" ? (
                <button className="btn" onClick={this.requestInvoice}>
                  Generate Rent Invoice
                </button>
              ) : (
                <button className="btn" onClick={this.requestLeaseInvoice}>
                  Generate Lease Invoice
                </button>
              )}
            </div>
          </>
        )}
        {/* --------------------------- */}
        {this.state.invoice_list.length !== 0 ? (
          <div className="robot__table">
            <table id="invoice_list">
              <thead>
                <tr>
                  <th align="right">
                    <b>Invoice ID</b>
                  </th>
                  <th align="right">
                    <b>Issued At</b>
                  </th>
                  <th align="right">
                    <b>Due Date</b>
                  </th>
                  <th align="right">
                    <b>Amount Due</b>
                  </th>
                  <th align="right">
                    <b>Status</b>
                  </th>
                </tr>
              </thead>
              <tbody>
                {this.state.invoice_list.map((invoice) => [
                  <tr key={invoice.invoiceId}>
                    <td align="right">{invoice.invoiceId}</td>
                    <td align="right">{invoice.issuedAt}</td>
                    <td align="right">{invoice.dueDate}</td>
                    <td align="right">{invoice.amountDue}</td>
                    <td align="right">{invoice.status}</td>
                  </tr>,
                ])}
              </tbody>
            </table>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    );
  }
}

export default AdminInvoice;

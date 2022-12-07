import React, { Component } from "react";
import axios from "axios";
import "./Sales.css";
import { Bar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";
Chart.register(...registerables);

const { API_URL } = require("../../../config/config").default;

class Sales extends Component {
  salesCoffeeTypeSuffix = "/business/salesbytype";
  salesRobotsSuffix = "/business/salesbyrobot";

  constructor(props) {
    super(props);
    this.state = {
      ct_start: "",
      ct_end: "",
      isCT: false,
      coffeeType: {},
      monthlyDrinks: {},
      sr_date: "",
      isSR: false,
      numSalesRobots: {},
    };
  }

  handleCTStartChanged = (e) => {
    this.setState({
      ct_start: e.target.value,
    });
  };

  handleCTEndChanged = (e) => {
    this.setState({
      ct_end: e.target.value,
    });
  };

  requestSalesType = (e) => {
    e.preventDefault();
    const { ct_start, ct_end } = this.state;
    var token = localStorage.getItem("apiAuthToken");
    var account = localStorage.getItem("localAccountId");
    var requestUrl =
      API_URL +
      this.salesCoffeeTypeSuffix +
      "?accountId=" +
      account +
      "&startDate=" +
      ct_start +
      "&endDate=" +
      ct_end;
    console.log("requestSalesType");
    axios
      .get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log("requestSalesType SUCCESS", response);
        this.setState({
          isCT: true,
          coffeeType: {
            labels: response.data["keys"],
            datasets: [
              {
                label: "total soft-drink sales",
                data: response.data["values"],
                backgroundColor: [
                  "rgba(255, 99, 132, 0.2)",
                  "rgba(255, 159, 64, 0.2)",
                  "rgba(255, 205, 86, 0.2)",
                ],
                borderColor: [
                  "rgb(255, 99, 132)",
                  "rgb(255, 159, 64)",
                  "rgb(255, 205, 86)",
                ],
                borderWidth: 1,
                barPercentage: 0.3,
              },
            ],
          },
        });
      })
      .catch((error) => {
        console.log("ERROR handleSearchSalesType");
        this.setState({ errorMessage: error.message });
      });
  };

  handleSRDateChanged = (e) => {
    this.setState({
      sr_date: e.target.value,
    });
    console.log("Date Selected", e.target.value);
  };

  requestSalesRobots = (e) => {
    e.preventDefault();
    const { sr_date } = this.state;
    var token = localStorage.getItem("apiAuthToken");
    var account = localStorage.getItem("localAccountId");

    var requestUrl =
      API_URL +
      this.salesRobotsSuffix +
      "?accountId=" +
      account +
      "&date=" +
      sr_date;
    console.log("requestSalesRobots:::", sr_date);
    axios
      .get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log("requestSalesRobots SUCCESS", response);
        this.setState({
          isSR: true,
          numSalesRobots: {
            labels: response.data["keys"],
            datasets: [
              {
                label: "Total Sales By Robots",
                data: response.data["values"],
                backgroundColor: "rgba(75, 192, 192, 0.2)",
                borderColor: "rgb(75, 192, 192)",
                borderWidth: 1,
                barPercentage: 0.3,
              },
            ],
          },
        });
      })
      .catch((error) => {
        console.log("ERROR requestSalesRobots");
        this.setState({ errorMessage: error.message });
      });
  };

  render() {
    return (
      <div>
        <div>
          <h1>
            <center>Soft-Drink Sales Dashboard</center>
          </h1>
        </div>

        <div className="stics__type">
          <h2>Total Sales (by Drinks)</h2>
          <form onSubmit={this.requestSalesType}>
            <tr>
              <td>
                <label htmlFor="start">Start</label>
              </td>
              <td>
                <input
                  style={{ marginTop: "10px", height: "10px" }}
                  type="date"
                  onChange={this.handleCTStartChanged}
                />
              </td>
            </tr>
            <tr>
              <td>
                <label htmlFor="end">End</label>
              </td>
              <td>
                <input
                  style={{ marginTop: "10px", height: "10px" }}
                  type="date"
                  onChange={this.handleCTEndChanged}
                />
              </td>
            </tr>
            <tr>
              <button className="btn" type="submit">
                Search
              </button>
            </tr>
          </form>
          {this.state.isCT ? (
            <div className="bar__graph">
              <div className="bar_drinks">
                <Bar data={this.state.coffeeType} />
              </div>
              <div className="bar_months">
                {/* <Bar data={this.state.coffeeType} /> */}
              </div>
            </div>
          ) : (
            <p />
          )}
        </div>
        <div className="stics__robots">
          <h2>Total Sales (by Robots)</h2>
          <form onSubmit={this.requestSalesRobots}>
            <label>Date</label>
            <input
              style={{ marginTop: "10px", height: "10px" }}
              type="date"
              onChange={this.handleSRDateChanged}
            />
            <button className="btn" type="submit">
              Search
            </button>
          </form>
          {this.state.isSR ? (
            <div className="bar__graph_robots">
              <Bar data={this.state.numSalesRobots} />
            </div>
          ) : (
            <p />
          )}
        </div>
      </div>
    );
  }
}

export default Sales;

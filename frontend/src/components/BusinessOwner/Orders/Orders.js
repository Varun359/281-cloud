import React, { Component } from "react";
import axios from "axios";
import "./Orders.css";
const { API_URL } = require("../../../config/config").default;

class Orders extends Component {
  getOrdersSuffix = "/business/allorders";

  constructor(props) {
    super(props);
    this.state = {
      orders: [],
    };
  }

  componentDidMount() {
    console.log("componentDidMount()");
    var requestUrl =
      API_URL +
      this.getOrdersSuffix +
      "?accountId=" +
      localStorage.getItem("localAccountId");

    console.log(requestUrl);
    var token = localStorage.getItem("apiAuthToken");

    axios
      .get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        this.setState({ orders: response.data });
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("Error :" + this.state.errorMessage);
      });
  }

  render() {
    return (
      <div>
        <h1>
          <center>Orders</center>
        </h1>
        <div className="order__table">
          <table id="orders_table">
            <thead>
              <tr>
                <th padding="default">
                  <b>Order ID</b>
                </th>
                <th padding="default">
                  <b>Robot ID</b>
                </th>
                <th align="right">
                  <b>Coffee</b>
                </th>
                <th align="right">
                  <b>Size</b>
                </th>
                <th align="right">
                  <b>Date</b>
                </th>
              </tr>
            </thead>
            <tbody>
              {this.state.orders.map((order) => [
                <tr key={order.orderId}>
                  <td padding="default">{order.orderId}</td>
                  <td padding="default">{order.robotId}</td>
                  <td align="right">{order.coffeeType}</td>
                  <td padding="default">{order.coffeeSize}</td>
                  <td align="right">{order.orderDate}</td>
                </tr>,
              ])}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default Orders;

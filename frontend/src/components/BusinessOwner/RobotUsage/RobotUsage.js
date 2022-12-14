import React, { Component } from "react";
import axios from "axios";
import "./RobotUsage.css";

const { API_URL } = require("../../../config/config").default;

class RobotUsage extends Component {
  getRobotUsageSuffix = "/account/robotusage";
  getRobotsSuffix = "/robot/account";

  constructor(props) {
    super(props);
    this.state = {
      selectedRobotId: "",
      robots: [],
      robotusages: {},
      isUsage: false,
    };
  }

  componentDidMount() {
    console.log("componentDidMount()");
    var requestGetRobotsUrl =
      API_URL +
      this.getRobotsSuffix +
      "?accountId=" +
      localStorage.getItem("localAccountId");
    console.log(requestGetRobotsUrl);
    var token = localStorage.getItem("apiAuthToken");

    axios
      .get(requestGetRobotsUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        this.setState({ robots: response.data });
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("There was an error: " + this.state.errorMessage);
      });
  }

  handleRobotSelected = (e) => {
    e.preventDefault();
    console.log("handleRobotSelected()");
    this.result = this.state.robots.find(
      (robot) => robot.robotId === e.target.value
    );
    console.log("selected:", this.result.robotId);
    this.setState({
      selectedRobotId: this.result.robotId,
    });
    this.getRobotUsage(this.result.robotId);
  };

  getRobotUsage(id) {
    console.log("getRobotUsage()");
    var requestUrl =
      API_URL +
      this.getRobotUsageSuffix +
      "?accountId=" +
      localStorage.getItem("localAccountId") +
      "&robotId=" +
      id;

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
        this.setState({
          robotusages: response.data,
          isUsage: true,
        });
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("Error :" + this.state.errorMessage);
      });
  }

  render() {
    console.log("render");

    return (
      <div>
        <h1>
          <center>Robot Usage</center>
        </h1>
        <div>
          <center>
            <label className="order__label">Available Robots: </label>
            <input
              className="order__input"
              list="location"
              onChange={this.handleRobotSelected}
            />
            <datalist id="location">
              {this.state.robots ? (
                this.state.robots.map((robot) => [
                  <option key={robot.robotId} value={robot.robotId} />,
                ])
              ) : (
                <option value="No available" />
              )}
            </datalist>
          </center>
        </div>

        {this.state.isUsage ? (
          <div className="robot_info">
            <table id="usage_info">
              <tr>
                <td>
                  <b>Robot ID</b>
                </td>
                <td>{this.state.selectedRobotId}</td>
              </tr>
              <tr>
                <td>
                  <b>Track Date</b>
                </td>
                <td>{this.state.robotusages.trackDate}</td>
              </tr>
              <tr>
                <td>
                  <b>Start Active Time</b>
                </td>
                <td>{this.state.robotusages.startActiveTime}</td>
              </tr>
              <tr>
                <td>
                  <b>End Active Time</b>
                </td>
                <td>{this.state.robotusages.endActiveTime}</td>
              </tr>
              <tr>
                <td>
                  <b>Total Orders</b>
                </td>
                <td>{this.state.robotusages.numOrders}</td>
              </tr>
            </table>
          </div>
        ) : (
          // <div>
          //   <br />
          //   <br />
          //   <b>Robot ID:</b> {this.state.selectedRobotId}
          //   <br />
          //   <br />
          //   <b>Track Date:</b> {this.state.robotusages.trackDate}
          //   <br />
          //   <br />
          //   <b>Start Active Time:</b> {this.state.robotusages.startActiveTime}
          //   <br />
          //   <br />
          //   <b>End Active Time:</b> {this.state.robotusages.endActiveTime}
          //   <br />
          //   <br />
          //   <b>Total Orders:</b> {this.state.robotusages.numOrders}
          //   <br />
          //   <br />
          // </div>
          <div></div>
        )}
      </div>
    );
  }
}

export default RobotUsage;

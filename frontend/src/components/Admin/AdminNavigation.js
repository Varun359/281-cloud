import React, { Component } from "react";
import axios from "axios";
import "./AdminNavigation.css";

const { API_URL } = require("../../config/config").default;

class AdminNavigation extends Component {
  getAllRobotSuffix = "/robot/all";

  armPitchSuffix = "/robot/arm/pitch";
  armRotateSuffix = "/robot/arm/rotate";

  basePitchSuffix = "/robot/base/pitch";
  baseYawSuffix = "/robot/base/yaw";

  gripperCloseSuffix = "/robot/gripper/close";
  gripperOpenSuffix = "/robot/gripper/open";
  gripperRotateSuffix = "/robot/gripper/rotate";
  gripperUpDownSuffix = "/robot/gripper/updown";

  resetSuffix = "/robot/reset";
  pauseSuffix = "/robot/pause";

  constructor(props) {
    super(props);
    this.state = {
      selectedRobotId: "",
      selectedRobotPos: 0,
      command: "",
      robots: [],
    };
  }

  componentDidMount() {
    console.log("componentDidMount()");
    var requestGetAllRobots = API_URL + this.getAllRobotSuffix;
    console.log(requestGetAllRobots);
    var token = localStorage.getItem("apiAuthToken");

    axios
      .get(requestGetAllRobots, {
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

  handleRobotId = (e) => {
    console.log("Robot ID changed " + e.target.value);
    this.setState({
      selectedRobotId: e.target.value,
    });
  };

  handleRobotPos = (e) => {
    console.log("position changed " + e.target.value);
    this.setState({
      selectedRobotPos: e.target.value,
    });
  };

  handleRobot(method) {
    console.log(method);
    var requestUrl = API_URL;
    switch (method) {
      case "reset":
        requestUrl += this.resetSuffix;
        break;
      case "pause":
        requestUrl += this.pauseSuffix;
        break;
      case "gripperClose":
        requestUrl += this.gripperCloseSuffix;
        break;
      case "gripperOpen":
        requestUrl += this.gripperOpenSuffix;
        break;
    }
    requestUrl += "?robotId=" + this.state.selectedRobotId;
    console.log(requestUrl);
    var token = localStorage.getItem("apiAuthToken");
    axios
      .post(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log(method + " SUCCESS ");
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("There was an error: " + this.state.errorMessage);
      });
  }

  handleRobotParam(method) {
    console.log(method);
    var requestUrl = API_URL;
    switch (method) {
      case "armPitch":
        requestUrl += this.armPitchSuffix;
        break;
      case "armRotate":
        requestUrl += this.armRotateSuffix;
        break;
      case "basePitch":
        requestUrl += this.basePitchSuffix;
        break;
      case "baseYaw":
        requestUrl += this.baseYawSuffix;
        break;
      case "gripperRotate":
        requestUrl += this.gripperRotateSuffix;
        break;
      case "gripperUpdown":
        requestUrl += this.gripperUpDownSuffix;
        break;
    }
    console.log(requestUrl);
    var token = localStorage.getItem("apiAuthToken");
    axios
      .post(
        requestUrl,
        {
          robotId: this.state.selectedRobotId,
          pos: this.state.selectedRobotPos,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        console.log(method + " SUCCESS ");
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
        console.log("There was an error: " + this.state.errorMessage);
      });
  }

  render() {
    return (
      <div>
        <h1>
          <center>Navigation Controls</center>
        </h1>
        <table>
          <tr>
            <td>Available Robots:</td>
            <td>
              <input
                style={{ marginTop: "10px", height: "10px" }}
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
            </td>
          </tr>
          <tr>
            <td>Movements:</td>
            <td>
              <input
                style={{ marginTop: "10px", height: "10px" }}
                type="text"
                onChange={this.handleRobotPos}
              />
            </td>
          </tr>
        </table>
        {/* <table>
          <div>
            <tr>
              <td>
                <label className="order__label">Available Robots: </label>
              </td>
              <td>
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
              </td>
            </tr>
          </div>
          <br></br>
          <div className="form-group">
            <tr>
              <td>
                <label>Movements: </label>
              </td>
              <td>
                <input type="text" onChange={this.handleRobotPos} />
              </td>
            </tr>
          </div>
        </table> */}

        <div class="row">
          <div class="column">
            <div class="card">
              <h3>Soft-Drink-Robot controller</h3>
              <div>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobot("reset")}
                >
                  Reset
                </button>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobot("pause")}
                >
                  Pause
                </button>
              </div>
            </div>
          </div>

          <div class="column">
            <div class="card">
              <h3>Soft-Drink-Robot Arm controller</h3>
              <div>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobotParam("armPitch")}
                >
                  Up & Down
                </button>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobotParam("armRotate")}
                >
                  Rotate
                </button>
              </div>
            </div>
          </div>

          <div class="column">
            <div class="card">
              <h3>Soft-Drink-Robot Base controller</h3>
              <div>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobotParam("basePitch")}
                >
                  Up & Down
                </button>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobotParam("baseYaw")}
                >
                  Left & Right
                </button>
              </div>
            </div>
          </div>

          <div class="column">
            <div class="card">
              <h3>Soft-Drink-Robot Gripper controller</h3>
              <div>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobot("gripperClose")}
                >
                  Close
                </button>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobot("gripperOpen")}
                >
                  Open
                </button>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobotParam("gripperRotate")}
                >
                  Rotate
                </button>
                <button
                  className="controller_btn"
                  onClick={() => this.handleRobotParam("gripperUpdown")}
                >
                  Updown
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      // <div>

      //   <div className="form-group">
      //       <label>Robot ID</label>
      //         <input list="location" onChange={this.handleRobotId} />
      //             <datalist id="location">
      //                 {this.state.robots ?
      //                     this.state.robots.map((robot)=> [
      //                             <option key={robot.robotId} value={robot.robotId} />
      //                     ]):
      //                     <option value="No available" />
      //                 }
      //             </datalist>
      //   </div>

      //   <div className="form-group">
      //       <label>Robot Position</label>
      //       <input type="text"  onChange={this.handleRobotPos}/>
      //   </div>

      //   <div> <h2> Robot controller </h2>
      //     <div>
      //       <button className='controller_btn' onClick={() => this.handleRobot('reset')}> Reset </button>
      //       <button className='controller_btn' onClick={() => this.handleRobot('pause')}> Pause </button>
      //     </div>
      //   </div>

      //   <div> <h2> Robot Arm controller </h2>
      //     <div>
      //       <button className='controller_btn' onClick={() => this.handleRobotParam('armPitch')}> Pitch </button>
      //       <button className='controller_btn' onClick={() => this.handleRobotParam('armRotate')}> Rotate </button>
      //     </div>
      //   </div>

      //   <div><h2> Robot Base controller </h2>
      //     <div>
      //       <button className='controller_btn'  onClick={() => this.handleRobotParam('basePitch')}> Pitch </button>
      //       <button className='controller_btn' onClick={() => this.handleRobotParam('baseYaw')}> Yaw </button>
      //     </div>

      //   </div>

      //   <div> <h2>Robot Gripper controller </h2>
      //     <div>
      //       <button className='controller_btn' onClick={() => this.handleRobot('gripperClose')}> Close </button>
      //       <button className='controller_btn' onClick={() => this.handleRobot('gripperOpen')}> Open </button>
      //       <button className='controller_btn' onClick={() => this.handleRobotParam('gripperRotate')}> rotate </button>
      //       <button className='controller_btn' onClick={() => this.handleRobotParam('gripperUpdown')}> updown </button>
      //     </div>

      //   </div>

      // </div>
    );
  }
}
export default AdminNavigation;

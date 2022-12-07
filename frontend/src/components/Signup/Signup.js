import React, { Component } from "react";
import { Redirect } from "react-router";
import axios from "axios";
import CloseLogin from "../Login/CloseLogin";
import "./Signup.css";

const { API_URL } = require("../../config/config").default;
class Signup extends Component {
  singupAccountSuffix = "/account/";
  singupBusinessUserSuffix = "/account/user";

  constructor(props) {
    super(props);
    this.state = {
      signupSuccess: false,
      fname: "",
      lname: "",
      email: "",
      password: "",
      businessname: "",
      address: "",
      city: "",
      state: "",
      zip: "",
      account: "",
      errorMessage: "",
      businessAccountExist: "",
    };
  }

  handleBusinessAccountExist = (e) => {
    this.setState({
      businessAccountExist: e.target.value,
    });
  };

  handleFNameChange = (e) => {
    this.setState({
      fname: e.target.value,
    });
  };

  handleLNameChange = (e) => {
    this.setState({
      lname: e.target.value,
    });
  };

  handleEmailChange = (e) => {
    this.setState({
      email: e.target.value,
    });
  };

  handlePasswordChange = (e) => {
    this.setState({
      password: e.target.value,
    });
  };

  handleBusinessnameChange = (e) => {
    this.setState({
      businessname: e.target.value,
    });
  };

  handleAddressChange = (e) => {
    this.setState({
      address: e.target.value,
    });
  };

  handleCityChange = (e) => {
    this.setState({
      city: e.target.value,
    });
  };

  handleStateChange = (e) => {
    this.setState({
      state: e.target.value,
    });
  };
  handleZipChange = (e) => {
    this.setState({
      zip: e.target.value,
    });
  };

  handleAccountChange = (e) => {
    this.setState({
      account: e.target.value === null ? 0 : e.target.value,
    });
  };

  handleSignup = (e) => {
    e.preventDefault();
    const { businessname, address, city, state, zip, account } = this.state;

    //if there is no accout, then create new account for business
    if (account === "") {
      var createBusinessUrl = API_URL + this.singupAccountSuffix;
      console.log("handleSignup() create business");
      axios
        .post(createBusinessUrl, {
          address: address,
          city: city,
          state: state,
          zip: zip,
          businessName: businessname,
        })
        .then((response) => {
          console.log("Create Business SUCCESS");
          console.log(response.data);
          localStorage.setItem("localAccountId", response.data["accountId"]);
          console.log(localStorage.getItem("localAccountId"));
          this.setState({
            account: response.data["accountId"],
          });
          this.createNewUser();
          window.location.reload(true);
        })
        .catch((error) => {
          console.log("ERROR: cannot create business");
          return;
        });
    } else {
      console.log("handleSignup() create user only with existing account");
      this.createNewUser();
    }
  };

  createNewUser() {
    const { fname, lname, email, password, account } = this.state;
    var createUserUrl = API_URL + this.singupBusinessUserSuffix;
    console.log("createNewUser()");
    axios
      .post(createUserUrl, {
        accountId: account,
        fname: fname,
        lname: lname,
        email: email,
        password: password,
      })
      .then((response) => {
        console.log("Create user SUCCESS");
        console.log(response);
        localStorage.setItem("localUserId", response.data["userId"]);
        console.log(localStorage.getItem("localUserId"));
        window.location.reload(true);
      })
      .catch((error) => {
        console.log("ERROR: cannot create user");
        return;
      });

    this.setState({
      signupSuccess: true,
    });
  }

  render() {
    const { businessAccountExist } = this.state;
    const { signupSuccess } = this.state;
    let redirect = <div></div>;
    if (signupSuccess) redirect = <Redirect to="/" />;

    return (
      // <div className="signup_form">
      //   {redirect}
      //   <form className="form__register" onSubmit={this.handleSignup}>
      //     <div className="form__title">Sign Up for Business</div>

      //     <div className="form__label"> Create New Business User: </div>
      //     <div className="form-group">
      //       <label>First Name</label>
      //       <input
      //         type="text"
      //         onChange={this.handleFNameChange}
      //         className="form-control"
      //         placeholder="Enter First Name"
      //       />
      //     </div>
      //     <div className="form-group">
      //       <label>Last Name</label>
      //       <input
      //         type="text"
      //         onChange={this.handleLNameChange}
      //         className="form-control"
      //         placeholder="Enter Last Name"
      //       />
      //     </div>
      //     <div className="form-group">
      //       <label>Email address</label>
      //       <input
      //         type="email"
      //         onChange={this.handleEmailChange}
      //         className="form-control"
      //         placeholder="Enter email"
      //       />
      //     </div>
      //     <div className="form-group">
      //       <label>Password</label>
      //       <input
      //         type="password"
      //         onChange={this.handlePasswordChange}
      //         className="form-control"
      //         placeholder="Enter password"
      //       />
      //     </div>
      //     <div className="form-group">
      //       <label>Business Account: </label>
      //       <input
      //         type="text"
      //         onChange={this.handleAccountChange}
      //         className="form-control"
      //         placeholder="If you have an account.."
      //       />
      //     </div>

      // <div className="form__label"> Create New Business: </div>
      // <div className="form-group">
      //   <label>Business Name</label>
      //   <input
      //     type="text"
      //     onChange={this.handleBusinessnameChange}
      //     className="form-control"
      //     placeholder="Enter Business Name"
      //   />
      // </div>
      // <div className="form-group">
      //   <label>Address</label>
      //   <input
      //     type="text"
      //     onChange={this.handleAddressChange}
      //     className="form-control"
      //     placeholder="Enter Address"
      //   />
      // </div>
      // <div className="form-group">
      //   <label>City</label>
      //   <input
      //     type="text"
      //     onChange={this.handleCityChange}
      //     className="form-control"
      //     placeholder="Enter City"
      //   />
      // </div>
      // <div className="form-group">
      //   <label>State</label>
      //   <input
      //     type="text"
      //     onChange={this.handleStateChange}
      //     className="form-control"
      //     placeholder="Enter State"
      //   />
      // </div>
      // <div className="form-group">
      //   <label>Zip</label>
      //   <input
      //     type="text"
      //     onChange={this.handleZipChange}
      //     className="form-control"
      //     placeholder="Enter Zip"
      //   />
      // </div>
      //     <button className="button" type="submit">
      //       <span>Sign Up</span>
      //     </button>
      //   </form>
      // </div>
      <>
        {redirect}
        <div className="bg-modal">
          <div className="modal-content">
            <CloseLogin />
            <div
              style={{
                marginTop: "30px",
                marginLeft: "20px",
                fontFamily: "Tahoma",
              }}
            >
              <h3>Create your account</h3>
              <p>Registration is easy.</p>
            </div>
            <form className="signin_form" onSubmit={this.handleSignup}>
              <div className="form-group">
                <label htmlFor="email">Email Address</label>
                <br />
                <input
                  type="email"
                  className="email"
                  id="email"
                  placeholder="Enter email"
                  onChange={this.handleEmailChange}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="username">First Name</label>
                <br />
                <input
                  type="text"
                  className="username"
                  id="username"
                  placeholder="Enter username"
                  onChange={this.handleFNameChange}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="username">Last Name</label>
                <br />
                <input
                  type="text"
                  className="username"
                  id="username"
                  placeholder="Enter username"
                  onChange={this.handleLNameChange}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="password">Password</label>
                <br />
                <input
                  type="password"
                  className="password"
                  id="password"
                  placeholder="Password"
                  onChange={this.handlePasswordChange}
                  required
                />
              </div>
              <div required>
                <div className="form-group_account">
                  <label htmlFor="password">
                    Do you have business account:
                  </label>
                </div>
                <div className="radio">
                  <label>
                    <input
                      type="radio"
                      value="Yes"
                      style={{ height: "15px", width: "15px" }}
                      checked={this.state.businessAccountExist === "Yes"}
                      onChange={this.handleBusinessAccountExist}
                    />
                    Yes
                  </label>
                </div>
                <div className="radio">
                  <label>
                    <input
                      type="radio"
                      value="No"
                      style={{ height: "15px", width: "15px" }}
                      checked={this.state.businessAccountExist === "No"}
                      onChange={this.handleBusinessAccountExist}
                    />
                    No
                  </label>
                </div>
              </div>
              {/* <h3>Current Value</h3> {businessAccountExist} */}
              {businessAccountExist === "Yes" && (
                <>
                  <div className="form__label"> </div>
                  <div className="form-group">
                    <label>Business Account ID:</label>
                    <input
                      type="text"
                      onChange={this.handleAccountChange}
                      className="form-control"
                      placeholder="Enter Account ID"
                    />
                  </div>
                </>
              )}

              {businessAccountExist === "No" && (
                <>
                  <div className="form__label"> Create New Business: </div>
                  <div className="form-group">
                    <label>Business Name</label>
                    <input
                      type="text"
                      onChange={this.handleBusinessnameChange}
                      className="form-control"
                      placeholder="Enter Business Name"
                    />
                  </div>
                  <div className="form-group">
                    <label>Address</label>
                    <input
                      type="text"
                      onChange={this.handleAddressChange}
                      className="form-control"
                      placeholder="Enter Address"
                    />
                  </div>
                  <div className="form-group">
                    <label>City</label>
                    <input
                      type="text"
                      onChange={this.handleCityChange}
                      className="form-control"
                      placeholder="Enter City"
                    />
                  </div>
                  <div className="form-group">
                    <label>State</label>
                    <input
                      type="text"
                      onChange={this.handleStateChange}
                      className="form-control"
                      placeholder="Enter State"
                    />
                  </div>
                  <div className="form-group">
                    <label>Zip</label>
                    <input
                      type="text"
                      onChange={this.handleZipChange}
                      className="form-control"
                      placeholder="Enter Zip"
                    />
                  </div>
                </>
              )}

              <div className="forgot_password"></div>
              <button
                // onClick={this.handle/}
                type="submit"
                className="btn btn-primary"
              >
                Register User
              </button>
            </form>
          </div>
        </div>
      </>
    );
  }
}

export default Signup;

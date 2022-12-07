import React, { Component } from "react";
import "./Landing.css";
import Signin from "../Login/Login";

class Landing extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showSignIn: false,
    };
  }

  popUpSignIn = (e) => {
    // console.log(this.showSignIn);
    this.setState({
      showSignIn: true,
    });
  };
  render() {
    return (
      <div className="landing">
        <video autoPlay loop muted playsInline className="back-video">
          <source
            src="https://res.cloudinary.com/dbjejqmz5/video/upload/v1668419742/992A106F-1A8F-4C74-9907-BC94F3B633E2_c0cgpy.mov"
            type="video/mp4"
          ></source>
        </video>
        <nav>
          <ul>
            <li>{/* <a href="/signup">Sign Up</a> */}</li>
            <li className="icons_nav" onClick={this.popUpSignIn}>
              {/* <a href="/login">Log In</a> */} Login
            </li>
          </ul>
        </nav>
        <div className="content">
          <h1>Soft-Drink-Maker Cloud Robot System</h1>
          <span>SCRS</span>
        </div>
        {this.state.showSignIn && (
          <Signin setshowSignIn={this.state.showSignIn} />
        )}
      </div>
    );
  }
}

export default Landing;

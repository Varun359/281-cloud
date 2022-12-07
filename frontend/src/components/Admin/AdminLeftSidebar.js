import React, { Component } from "react";
import { Link } from "react-router-dom";
import "./AdminLeftSidebar.css";

class AdminLeftSidebar extends Component {
  render() {
    return (
      <div className="header_items">
        <div className="dashboard_item">
          <div className="heading">
            <h2>Soft-Drink-Maker Cloud Robot System</h2>
          </div>
          <div className="dashboard_items">
            <Link style={{ textDecoration: "none" }} to="/adminhome">
              <div className="dashboard_item">
                <img
                  className="dashboard_image"
                  src="https://www.k-loops.com/wp-content/uploads/2022/02/Immagine-Home-Sito-K-LOOPS-1-1.jpg"
                  alt="home"
                ></img>
                <h3 style={{ marginLeft: "19px", fontSize: "20px" }}>Home</h3>
              </div>
            </Link>
            <Link style={{ textDecoration: "none" }} to="/adminstatistics">
              <div className="dashboard_item">
                <img
                  className="dashboard_image"
                  src="https://s40424.pcdn.co/in/wp-content/uploads/2022/05/sales-interview.jpg"
                  alt="home"
                ></img>
                <h3 style={{ marginLeft: "11px", fontSize: "20px" }}>
                  Statistics
                </h3>
              </div>
            </Link>
            <Link style={{ textDecoration: "none" }} to="/admininvoice">
              <div className="dashboard_item">
                <img
                  className="dashboard_image"
                  src="https://www.stampli.com/wp-content/uploads/2020/02/01-STAMPLI_Invoice_Documentation_Hero.png"
                  alt="home"
                ></img>
                <h3 style={{ marginLeft: "18px", fontSize: "20px" }}>
                  Invoice
                </h3>
              </div>
            </Link>
            <Link style={{ textDecoration: "none" }} to="/adminnavigation">
              <div className="dashboard_item">
                <img
                  className="dashboard_image"
                  src="https://thumbs.dreamstime.com/b/businesswoman-controlling-robot-remote-control-office-desk-artificial-intelligence-technology-business-concept-187226178.jpg"
                  alt="home"
                ></img>
                <h3 style={{ marginLeft: "14px", fontSize: "20px" }}>
                  Controls
                </h3>
              </div>
            </Link>
          </div>
        </div>
      </div>
      // <div className="adminleftsidebar">
      //   <Link style={{ textDecoration: "none" }} to="/adminhome">
      //     <div className="adminleftsidebar__tab">
      //       <span>Home</span>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/adminstatistics">
      //     <div className="adminleftsidebar__tab">
      //       <span>Statistics</span>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/admininvoice">
      //     <div className="adminleftsidebar__tab">
      //       <span>Invoice</span>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/adminnavigation">
      //     <div className="adminleftsidebar__tab">
      //       <span>Robot Controls</span>
      //     </div>
      //   </Link>
      // </div>
    );
  }
}

export default AdminLeftSidebar;

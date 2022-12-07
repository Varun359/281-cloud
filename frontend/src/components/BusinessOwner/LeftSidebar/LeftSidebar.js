import React, { Component } from "react";
import { Link } from "react-router-dom";
import "./LeftSidebar.css";

class LeftSidebar extends Component {
  render() {
    return (
      <div className="header_items">
        <div className="heading">
          <h2>Soft-Drink-Maker Cloud Robot System</h2>
        </div>
        <div className="dashboard_items">
          <Link style={{ textDecoration: "none" }} to="/dashboard">
            <div className="dashboard_item">
              <img
                className="dashboard_image"
                src="https://t4.ftcdn.net/jpg/02/10/96/95/360_F_210969565_cIHkcrIzRpWNZzq8eaQnYotG4pkHh0P9.jpg"
                alt="home"
              ></img>
              <h3 style={{ marginLeft: "10px", fontSize: "20px" }}>Robots</h3>
            </div>
          </Link>
          <Link style={{ textDecoration: "none" }} to="/sales">
            <div className="dashboard_item">
              <img
                className="dashboard_image"
                src="https://s40424.pcdn.co/in/wp-content/uploads/2022/05/sales-interview.jpg"
                alt="home"
              ></img>
              <h3 style={{ marginLeft: "20px", fontSize: "20px" }}>Sales</h3>
            </div>
          </Link>
          <Link style={{ textDecoration: "none" }} to="/invoice">
            <div className="dashboard_item">
              <img
                className="dashboard_image"
                src="https://www.stampli.com/wp-content/uploads/2020/02/01-STAMPLI_Invoice_Documentation_Hero.png"
                alt="home"
              ></img>
              <h3 style={{ marginLeft: "18px", fontSize: "20px" }}>Invoice</h3>
            </div>
          </Link>
          <Link style={{ textDecoration: "none" }} to="/orders">
            <div className="dashboard_item">
              <img
                className="dashboard_image"
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRW5xmj_vJ0ZZ9uHBNsgR5erWv76cf0C05SQg&usqp=CAU"
                alt="home"
              ></img>
              <h3 style={{ marginLeft: "19px", fontSize: "20px" }}>Orders</h3>
            </div>
          </Link>
          <Link style={{ textDecoration: "none" }} to="/navigation">
            <div className="dashboard_item">
              <img
                className="dashboard_image"
                src="https://thumbs.dreamstime.com/b/businesswoman-controlling-robot-remote-control-office-desk-artificial-intelligence-technology-business-concept-187226178.jpg"
                alt="home"
              ></img>
              <h3 style={{ marginLeft: "14px", fontSize: "20px" }}>Controls</h3>
            </div>
          </Link>
          <Link style={{ textDecoration: "none" }} to="/robotusage">
            <div className="dashboard_item">
              <img
                className="dashboard_image"
                src="https://cdn.sickcn.com/media/ZOOM/2/02/302/IM0085302.png"
                alt="home"
              ></img>
              <h3 style={{ marginLeft: "21px", fontSize: "20px" }}>Usage</h3>
            </div>
          </Link>
        </div>
      </div>
      // <div className="leftsidebar">
      //   <Link style={{ textDecoration: "none" }} to="/dashboard">
      //     <div className="leftsidebar__tab">
      //       <img
      //         className="dashboard_image"
      //         src="https://images.pexels.com/photos/1129413/pexels-photo-1129413.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
      //         alt="home"
      //       ></img>
      //       <h3>Robots</h3>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/sales">
      //     <div className="leftsidebar__tab">
      //       <img
      //         className="dashboard_image"
      //         src="https://images.pexels.com/photos/1129413/pexels-photo-1129413.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
      //         alt="home"
      //       ></img>
      //       <span>Sales</span>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/invoice">
      //     <div className="leftsidebar__tab">
      //       <img
      //         className="dashboard_image"
      //         src="https://images.pexels.com/photos/1129413/pexels-photo-1129413.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
      //         alt="home"
      //       ></img>
      //       <h3>Invoice</h3>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/orders">
      //     <div className="leftsidebar__tab">
      //       <img
      //         className="dashboard_image"
      //         src="https://images.pexels.com/photos/1129413/pexels-photo-1129413.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
      //         alt="home"
      //       ></img>
      //       <span>Orders</span>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/navigation">
      //     <div className="leftsidebar__tab">
      //       <img
      //         className="dashboard_image"
      //         src="https://images.pexels.com/photos/1129413/pexels-photo-1129413.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
      //         alt="home"
      //       ></img>
      //       <span>Robot Controls</span>
      //     </div>
      //   </Link>

      //   <Link style={{ textDecoration: "none" }} to="/robotusage">
      //     <div className="leftsidebar__tab">
      //       <img
      //         className="dashboard_image"
      //         src="https://images.pexels.com/photos/1129413/pexels-photo-1129413.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
      //         alt="home"
      //       ></img>
      //       <span>Robot Usage</span>
      //     </div>
      //   </Link>
      // </div>
    );
  }
}

export default LeftSidebar;

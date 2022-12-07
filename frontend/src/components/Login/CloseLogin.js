import React from "react";
import "./Login.css";

function CloseLogin() {
  const showHomePage = () => {
    // setshowSignIn(false);
    window.location.reload(true);
  };

  return (
    <>
      <div className="bg-modal1">
        <div className="modal-content1">
          <div className="signin_close">
            <p onClick={showHomePage}>X</p>
          </div>
        </div>
      </div>
    </>
  );
}

export default CloseLogin;

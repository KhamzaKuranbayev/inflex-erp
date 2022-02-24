import React from 'react';
import {Link} from "react-router-dom";
import logoImg from "./../../../../assets/images/logo.png"

function Sidebar({title="",img=""}) {
    return (
        <div className="auth__sidebar">
            <Link to="/">
                <img src={logoImg} alt="logo" />
            </Link>
            <div className="auth__sidebar-content">
                <h2>{title}</h2>
                <img src={img} alt="bg" className="img-fluid" />
            </div>
        </div>
    )
}

export default Sidebar

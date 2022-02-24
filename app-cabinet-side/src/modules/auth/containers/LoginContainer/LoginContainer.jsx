import React, {Component} from 'react';
import {connect} from "react-redux";
import Sidebar from '../../components/Sidebar/Sidebar';
import sidebarImg from "../../../../assets/images/auth/auth_login_bg.png";

class LoginContainer extends Component {
    render() {
        return (
            <div className="auth__login">
                <div className="auth__login-left">
                    <Sidebar title="PDP Academyga Xush kelibsiz !" img={sidebarImg}/>
                </div>
                <div className="auth__login-right">
                    right block
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {}
}

const mapDispatchToProps = (dispatch) => {
    return {}
}

export default connect(mapStateToProps, mapDispatchToProps)(LoginContainer);

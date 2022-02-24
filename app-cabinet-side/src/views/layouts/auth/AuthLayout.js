import React, {Component} from 'react';

class AuthLayout extends Component {
    render() {
        const {children} = this.props;
        return (
            <div className="layout__auth wrapper">
                {children}
            </div>
        );
    }
}

export default AuthLayout;
import React, {Component} from 'react';

class ErrorLayout extends Component {
    render() {
        const {children} = this.props;
        return (
            <div>
                Error Layout Top
                {children}
                Error Layout Footer
            </div>
        );
    }
}

export default ErrorLayout;
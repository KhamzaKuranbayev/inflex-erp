import React, {Component} from 'react';

class MainLayout extends Component {
    render() {
        const {children} = this.props;
        return (
            <>
                {/* <h1>Top main</h1> */}
                {children}
                {/* <h1>Footer main</h1> */}
            </>
        );
    }
}

export default MainLayout;
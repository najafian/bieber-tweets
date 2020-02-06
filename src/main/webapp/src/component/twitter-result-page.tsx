import React from 'react';
import {RouteComponentProps} from "react-router";
import {IRootState} from "../shared/reducer";
import {connect} from "react-redux";

interface ICardInstituteProps extends StateProps, DispatchProps, RouteComponentProps<{}> {
}

class TwitterResultPage extends React.Component<ICardInstituteProps> {
    render(): React.ReactElement<any, string | React.JSXElementConstructor<any>> | string | number | {} | React.ReactNodeArray | React.ReactPortal | boolean | null | undefined {
        return <div>ccdf</div>;
    }
}

const mapStateToProps = ({twitterApiReducer}: IRootState) => ({
    twitterApiReducer
});

const mapDispatchToProps = {};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TwitterResultPage);
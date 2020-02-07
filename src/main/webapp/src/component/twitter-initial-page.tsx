import React from 'react';
import {RouteComponentProps} from "react-router";
import {IRootState} from "../shared/reducer";
import {TextBox} from '@syncfusion/ej2-inputs';
import {Button} from '@syncfusion/ej2-buttons';
import {connect} from "react-redux";
import {loadPinIDFromTwitterUri, getResultFromTwitter, getResultFromDB} from "./reducer/twitter-reducer";

interface ICardInstituteProps extends StateProps, DispatchProps, RouteComponentProps<{}> {
}

class TwitterInitialPage extends React.Component<ICardInstituteProps> {
    private appName: TextBox = new TextBox();
    private consumeKey: TextBox = new TextBox();
    private consumeSecurityKey: TextBox = new TextBox();
    private PinID: TextBox = new TextBox();
    private keywordInput: TextBox = new TextBox();
    private PinIDBtn: Button;
    private showResultBtn: Button;
    private ResultFromTwitterBtn: Button;

    constructor(props: any) {
        super(props);
        this.PinIDBtn = new Button();
        this.showResultBtn = new Button();
        this.ResultFromTwitterBtn = new Button();

    }

    componentDidUpdate(prevProps: Readonly<ICardInstituteProps>, prevState: Readonly<{}>, snapshot?: any): void {
        if (this.props.twitterApiReducer.twitterApiUrl !== prevProps.twitterApiReducer.twitterApiUrl) {
            window.open(this.props.twitterApiReducer.twitterApiUrl);
        }
        let twitterApiGetAndSave = this.props.twitterApiReducer.twitterApiGetAndSave;
        if (twitterApiGetAndSave !== prevProps.twitterApiReducer.twitterApiGetAndSave) {
            let resultTweets = JSON.stringify(twitterApiGetAndSave);
            console.log(resultTweets);
            console.log(twitterApiGetAndSave.length);
        }
        let twitterApiGetResultFromDB = this.props.twitterApiReducer.twitterApiGetResultFromDB;
        if (twitterApiGetResultFromDB !== prevProps.twitterApiReducer.twitterApiGetResultFromDB) {
            console.log(twitterApiGetResultFromDB);
            this.props.history.push('/result-page', {result: twitterApiGetResultFromDB});
        }
    }

    componentDidMount(): void {
        const initializeInputBox = (input: TextBox, title: string, value: string, ID: string) => {
            input.placeholder = title;
            input.floatLabelType = 'Auto';
            input.value = value;
            input.appendTo('#' + ID);
        };
        initializeInputBox(this.appName, 'Application Name', 'java-exercise', 'appNameID');
        initializeInputBox(this.consumeKey, 'consume Key ID', 'RLSrphihyR4G2UxvA0XBkLAdl', 'consumeKeyID');
        initializeInputBox(this.consumeSecurityKey, 'consume Security KeyID', 'FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4', 'consumeSecurityKeyID');
        initializeInputBox(this.PinID, 'Pin ID', '', 'PinIDInput');
        initializeInputBox(this.keywordInput, 'Keyword search', 'Bieber', 'keywordInput');
        this.PinIDBtn.appendTo('#PinIDBtn');
        this.ResultFromTwitterBtn.appendTo('#saveInDBBtn');
        this.showResultBtn.appendTo('#showResultBtn');

        this.getPinUri();
        this.getDataFromTwitter();
        this.showResult();
    }

    private showResult() {
        this.props.history.push('/main-rps-game');
        this.showResultBtn.element.addEventListener('click', () => {
            this.props.getResultFromDB();
        });
    }

    private getDataFromTwitter() {
        this.ResultFromTwitterBtn.element.addEventListener('click', () => {
            let conKey = this.consumeKey.value;
            let conSecKey = this.consumeSecurityKey.value;
            let pinID = this.PinID.value;
            let keywordSearch = this.keywordInput.value;
            if (conKey.length > 0 &&
                conSecKey.length > 0 &&
                keywordSearch.length > 0)
                this.props.getResultFromTwitter({
                    applicationName: this.appName.value,
                    consumerKey: conKey,
                    consumerSecurityKey: conSecKey,
                    keywordSearch: keywordSearch,
                    pinID: pinID
                });
            else
                alert('please fill all inputs!');
        });
    }

    private getPinUri() {
        this.PinIDBtn.element.addEventListener('click', () => {
            this.PinID.value = '';
            let conKey = this.consumeKey.value;
            let conSecKey = this.consumeSecurityKey.value;
            if (conKey.length > 0 && conSecKey.length > 0)
                this.props.loadPinIDFromTwitterUri({
                    consumerKey: conKey,
                    consumerSecretKey: conSecKey
                });
            else
                alert('please fill consumerKey and consumerSecurityKey!');
        });
    }

    render(): any {
        return <div>
            <div className="panel-box">
                <div className="col-md-10" style={{margin: '0 auto'}}>
                    <div className="image-logo"/>
                    <div className={"row"}>
                        <div className="col-md-6"><input id="appNameID"/></div>
                        <div className="col-md-6"><input id="keywordInput"/></div>
                    </div>
                    <div className="topPadding">
                        <input id="consumeKeyID"/>
                    </div>
                    <div className="topPadding">
                        <input id="consumeSecurityKeyID"/>
                    </div>
                    <div className="topPadding">
                        <div id="PinIDBtn">Get PinID From Twitter</div>
                    </div>
                    <div className="topPadding">
                        <input id="PinIDInput"/>
                    </div>
                    <div className="topPadding">
                        <div id="saveInDBBtn">Get tweets and save into DB</div>
                    </div>
                    <div className="topPadding">
                        <div id="showResultBtn">Show Result</div>
                    </div>

                </div>
            </div>
        </div>;
    }
}

const mapStateToProps = ({twitterApiReducer}: IRootState) => ({
    twitterApiReducer
});

const mapDispatchToProps = {
    loadPinIDFromTwitterUri,
    getResultFromTwitter,
    getResultFromDB
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TwitterInitialPage);
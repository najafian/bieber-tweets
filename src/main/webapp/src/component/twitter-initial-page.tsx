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
    private numberOfRecord = 0;

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
            this.numberOfRecord = twitterApiGetAndSave;
            this.showResultBtn.disabled = false;
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
        this.showResultBtn.disabled = true;
        this.getPinUri();
        this.getDataFromTwitter();
        this.showResult();
    }

    private showResult() {
        this.props.history.push('/sytac');
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
                keywordSearch.length > 0) {
                this.props.getResultFromTwitter({
                    applicationName: this.appName.value,
                    consumerKey: conKey,
                    consumerSecurityKey: conSecKey,
                    keywordSearch: keywordSearch,
                    pinID: pinID
                });
                this.ResultFromTwitterBtn.disabled = true;
            } else
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
                    <div className="row topPadding">
                        <div className="col-md-5">
                            <button style={{position: 'absolute', bottom: '0px'}} id="PinIDBtn">Get PinID From Twitter
                            </button>
                        </div>
                        <div className="col-md-7"><input id="PinIDInput"/></div>
                    </div>
                    <hr/>
                    <div className={"row topPadding"}>
                        <div className="col-md-6">
                            <div id="saveInDBBtn">Get tweets and save into DB</div>
                        </div>
                        <div className="col-md-6"><label style={{width: '100%', fontSize: '10px'}}>A number of records
                            are saved in
                            database: {this.numberOfRecord}</label></div>

                    </div>

                    <div className="topPadding">
                        <button id="showResultBtn">Show Result</button>
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
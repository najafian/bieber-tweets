import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from "react-redux";
import './contents/styles/index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import '@syncfusion/ej2/bootstrap.css';
import './index.css';
import * as serviceWorker from './serviceWorker';
import initialize from "./shared/reducer/store";
import {Route, Router, Switch} from "react-router";
import {createBrowserHistory} from 'history'
import TwitterAuthPage from './component/twitter-initial-page'
import TwitterResultPage from './component/twitter-result-page'


export const globalStore = initialize();

ReactDOM.render(
    <Provider store={globalStore}>
        <div className="fullScreen-page-class">
            <div className="image-background-class">
                <div className="center-form-class">
                    <Router history={createBrowserHistory()}>
                        <Switch>
                            <Route path="/result-page" component={TwitterResultPage}/>
                            <Route component={TwitterAuthPage} path="/"/>
                        </Switch>
                    </Router>
                </div>
            </div>
            <div className="page-footer-class">This is Demo Project By Mehdi Najafian</div>
            <div id={"loadingPanel"}>
                <div className="loader"/>
            </div>
        </div>
    </Provider>
    , document.getElementById('root'));

serviceWorker.unregister();

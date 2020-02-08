import {combineReducers} from 'redux';
import twitterApiReducer, {TwitterApiReducerType} from "../../component/reducer/twitter-reducer";


export interface IRootState {
    readonly twitterApiReducer: TwitterApiReducerType;
}

const rootReducer = combineReducers<IRootState>({
    twitterApiReducer
});

export default rootReducer;
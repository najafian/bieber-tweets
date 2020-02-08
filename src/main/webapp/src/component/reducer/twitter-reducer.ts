import axios from 'axios';
import {IPayloadResult} from "../../shared/type/IPayload";
import {FAILURE, REQUEST, SUCCESS} from "../../shared/type/action-case";
import {IParamInput} from "../../shared/type/IParamInput";


export const ACTION_TYPES = {
    TWITTER_URL: ':api/TWITTER_URL',
    TWITTER_RESULT: ':api/TWITTER_RESULT',
    TWITTER_SAVE: ':api/TWITTER_SAVE'
};

const initialState = {
    twitterApiUrl: '',
    twitterApiGetAndSave: 0,
    twitterApiGetResultFromDB: []
};

export type TwitterApiReducerType = Readonly<typeof initialState>;

// Reducer
export default (state: TwitterApiReducerType = initialState, action: any): TwitterApiReducerType => {
    switch (action.type) {
        case REQUEST(ACTION_TYPES.TWITTER_URL):
        case REQUEST(ACTION_TYPES.TWITTER_RESULT):
        case REQUEST(ACTION_TYPES.TWITTER_SAVE):
            return {
                ...state,
            };
        case FAILURE(ACTION_TYPES.TWITTER_URL):
        case FAILURE(ACTION_TYPES.TWITTER_RESULT):
        case FAILURE(ACTION_TYPES.TWITTER_SAVE):
            return {
                ...state,
            };
        case SUCCESS(ACTION_TYPES.TWITTER_URL):
            return {
                ...state,
                twitterApiUrl: action.payload.data
            };
        case SUCCESS(ACTION_TYPES.TWITTER_RESULT):
            return {
                ...state,
                twitterApiGetResultFromDB: action.payload.data
            };
        case SUCCESS(ACTION_TYPES.TWITTER_SAVE):
            return {
                ...state,
                twitterApiGetAndSave: action.payload.data
            };
        default:
            return state;
    }
};

// Actions
export const loadPinIDFromTwitterUri: IPayloadResult<any> = (entity: IParamInput) => ({
    type: ACTION_TYPES.TWITTER_URL,
    payload: axios.get('/api/GenerateUrl?consumerKey=' + entity.consumerKey + '&consumerSecretKey=' + entity.consumerSecretKey)
});

export const getResultFromTwitter: IPayloadResult<any> = (entity: any) => ({
    type: ACTION_TYPES.TWITTER_SAVE,
    payload: axios.put('/api', entity)
});

export const getResultFromDB: IPayloadResult<any> = (entity: any) => ({
    type: ACTION_TYPES.TWITTER_RESULT,
    payload: axios.get('/api', entity)
});
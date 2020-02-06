import {AxiosPromise} from "axios";

export interface IPayload<T> {
    type: string;
    payload: AxiosPromise<T>;
    meta?: any;
}

export type IPayloadResult<T> = (data?: any, page?: number, size?: number, sort?: string) => IPayload<T> | ((dispatch: any) => IPayload<T>);



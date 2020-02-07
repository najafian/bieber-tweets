import React from 'react';
import {RouteComponentProps} from "react-router";
import {Grid, Sort, Filter, Selection, Page} from '@syncfusion/ej2-grids';

interface IResult {
    user: string;
    userDate: string;
    text: string;
    twitterDate: string;
    screenName: string;
}

Grid.Inject(Selection, Sort, Filter, Page);
export default class TwitterResultPage extends React.Component<RouteComponentProps<{}>> {
    grid: Grid;

    constructor(props: any) {
        super(props);
        this.grid = new Grid();
    }

    convertDateFormat(result: any): any {
        const convertDate = (dateTime: string) => {
            let date = new Date(dateTime);
            let date1 = date.getDate();
            let fullYear = date.getFullYear();
            let month = date.getMonth() + 1;
            let hours = date.getHours();
            let minutes = date.getMinutes();
            return (fullYear + '/' + month + '/' + date1 + ' ' + hours + ':' + minutes);
        };
        return (result as IResult[]).map(a => {
            return {...a, userDate: convertDate(a.userDate), twitterDate: convertDate(a.twitterDate)}
        })
    }

    componentDidMount(): void {
        let state = this.props.location.state;
        let listResult = this.convertDateFormat((this.props.location.state as any).result);
        console.log('state: ', state);
        this.grid = new Grid({
            dataSource: listResult,
            allowSelection: true,
            allowFiltering: true,
            allowPaging: true,
            pageSettings: {pageSize: 13},
            allowSorting: true,
            filterSettings: {type: 'Menu'},
            enableHover: false,
            height: 380,
            enableAltRow: true,
            rowHeight: 29,
            gridLines: 'Both',
            columns: [
                {
                    field: 'user',  headerText: 'User', isPrimaryKey: true, width: '130',clipMode: 'EllipsisWithTooltip'
                }, {
                    field: 'userDate', headerText: 'userDate', width: '90',
                    filter: {type: 'Menu'}
                }, {
                    field: 'text', headerText: 'tweet', width: '170',
                    filter: {type: 'CheckBox'}, clipMode: 'EllipsisWithTooltip'
                }, {
                    field: 'twitterDate', headerText: 'tweet Date', width: '90', filter: {type: 'Menu'}
                }, {
                    field: 'screenName', width: '140', headerText: 'screenName', filter: {type: 'CheckBox'},
                }
            ]
        });
        this.grid.appendTo('#grid');
    }

    render(): React.ReactElement<any, string | React.JSXElementConstructor<any>> | string | number | {} | React.ReactNodeArray | React.ReactPortal | boolean | null | undefined {
        return <div>
            <div className="panel-box" style={{width: '1000px'}}>
                <div id="grid"/>
            </div>
        </div>;
    }
}
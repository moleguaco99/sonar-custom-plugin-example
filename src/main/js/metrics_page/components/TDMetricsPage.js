import {findMetrics} from "../../common/api";

export default class TDMetricsPage extends React.PureComponent {
    state = {
        loading: true,
        data: {}
    };

    componentDidMount() {
        findMetrics(this.props.project).then(data => {
            this.setState({
                loading: false,
                data
            });
        });
    }

    render() {
        if (this.state.loading) {
            return (
                <div className="page page-limited">
                    Loading...
                </div>
            );
        }

        return (
            <div className="page page-limited">
                {data.reliability_technical_debt}
                {data.security_technical_debt}
                {data.extended_technical_debt}
            </div>
        );
    }
}
import React from 'react';
import { render } from 'react-dom';
import { Line } from 'react-chartjs-2';
import axios from 'axios';

class Ranking extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            dataset: null,
            labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17]
        };
    }

    componentDidMount() {
        axios.get('/api/rankings/' + this.props.name + '/' + this.props.week)
            .then(res => {
                this.setState({dataset: res.data}, () => {
                    this.modifyDataset();
                    this.generateLabels();
                });
            });
    }

    modifyDataset() {
        let dataset = this.state.dataset;
        dataset.map(data => {
            data.fill = false;
            data.borderColor = this.getTeamColor(data.label);
        });
        this.setState({dataset: dataset});
    }

    getTeamColor(team) {
        const colorArray = [
            ['Cardinals', '#9B2743'],
            ['Falcons', '#A6192E'],
            ['Ravens', '#241773'],
            ['Bills', '#00338D'],
            ['Panthers', '#0085CA'],
            ['Bears', '#DC4405'],
            ['Bengals', '#FC4C02'],
            ['Browns', '#382F2D'],
            ['Cowboys', '#041E42'],
            ['Broncos', '#FC4C02'],
            ['Lions', '#0069B1'],
            ['Packers', '#175E33'],
            ['Texans', '#A6192E'],
            ['Colts', '#001489'],
            ['Jaguars', '#D49F12'],
            ['Chiefs', '#C8102E'],
            ['Rams', '#002244'],
            ['Dolphins', '#008E97'],
            ['Vikings', '#512D6D'],
            ['Patriots', '#C8102E'],
            ['Saints', '#A28D5B'],
            ['Giants', '#001E62'],
            ['Jets', '#0C371D'],
            ['Raiders', '#101820'],
            ['Eagles', '#004851'],
            ['Steelers', '#FFB81C'],
            ['Chargers', '#0072CE'],
            ['49ers', '#9B2743'],
            ['Seahawks', '#4DFF00'],
            ['Buccaneers', '#C8102E'],
            ['Titans', '#4B92DB'],
            ['Redskins', '#862633'],
        ];
        const colors = new Map(colorArray);
        let color = colors.get(team);
        if (color == undefined) throw 'Could not find team color for the following team: ' + team + '!';
        return color;
    }

    generateLabels() {
        let data = this.state.dataset;
        let labels = [];
        for (let i = 0; i < data[0].data.length; i++) {
            labels.push(i);
        }
        this.setState({labels: labels});
    }

    render() {
        let data = {
            labels: this.state.labels,
            datasets: this.state.dataset,
        };

        let options = {
            elements: {
                line: {
                    tension: 0
                }
            },
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Ranking Points'
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Week'
                    }
                }]
            }
        };

        return (
            <div>
                <Line data={data} options={options}/>
            </div>
        );
    }
}

export default Ranking;
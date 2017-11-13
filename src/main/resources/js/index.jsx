import React from 'react';
import { render } from 'react-dom';
import Ranking from './Ranking.jsx';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            maxWeek: 17,
            week: 9
        };
    }

    setWeek(e) {
        this.setState({week: e.target.value});
        console.log('Set week to ' + e.target.value);
    }

    getWeeksArray() {
        const array = [];
        for (let i = 1; i <= this.state.maxWeek; i++) {
            array.push(i);
        }
        return array;
    }

    render () {
        const array = this.getWeeksArray();
        const selectOptions = array.map((e) =>
            <option key={e} value={e}>{e}</option>
        );

        return (
            <div>
                <h1>Footballer</h1>

                <div>
                    Week:
                    <select defaultValue={this.state.week} onChange={this.setWeek.bind(this)}>{selectOptions}</select>
                </div>

                <h2>SelfBased</h2>
                <Ranking name={'selfbased'} week={this.state.week} />

                <h2>EvenPlay</h2>
                <Ranking name={'evenplay'} week={this.state.week} />

                <h2>AdjustedWins</h2>
                <Ranking name={'adjustedwins'} week={this.state.week} />
            </div>
        );
    }
}

render(<App/>, document.getElementById('app'));
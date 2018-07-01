import React from 'react';
import { render } from 'react-dom';
import Ranking from './Ranking.jsx';
import axios from 'axios';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      maxWeek: 17,
      week: 9,
      divisions: ['None'],
      currentDivision: 'None'
    };
  }

  componentDidMount() {
    axios.get('/api/divisions')
      .then(res => {
        this.setState({divisions: this.state.divisions.concat(res.data)});
      });
  }

  setWeek(e) {
    this.setState({week: e.target.value});
  }

  getWeeksArray() {
    const array = [];
    for (let i = 1; i <= this.state.maxWeek; i++) {
      array.push(i);
    }
    return array;
  }

  setDivision(e) {
    this.setState({currentDivision: e.target.value});
  }

  render () {
    const weeksArray = this.getWeeksArray();
    const weekSelectOptions = weeksArray.map((e) =>
      <option key={e} value={e}>{e}</option>
    );

    let divisionSelectOptions = this.state.divisions.map((e) =>
      <option key={e} value={e}>{e}</option>
    );

    return (
      <div>
        <h1>Footballer</h1>

        <div id='selectors'>
          <span>Week:</span>
          <select id='first-selector' defaultValue={this.state.week} onChange={this.setWeek.bind(this)}>{weekSelectOptions}</select>

          <span>Division:</span>
          <select defaultValue={this.state.currentDivision} onChange={this.setDivision.bind(this)}>{divisionSelectOptions}</select>
        </div>

        <h2>SelfBased</h2>
        <Ranking name={'selfbased'} week={this.state.week} divisionString={this.state.currentDivision} />

        <h2>EvenPlay</h2>
        <Ranking name={'evenplay'} week={this.state.week} divisionString={this.state.currentDivision} />

        <h2>AdjustedWins</h2>
        <Ranking name={'adjustedwins'} week={this.state.week} divisionString={this.state.currentDivision} />
      </div>
    );
  }
}

render(<App/>, document.getElementById('app'));
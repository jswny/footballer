import React from 'react';
import { render } from 'react-dom';
import Ranking from './Ranking.jsx';
import axios from 'axios';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      year: 2018,
      maxWeek: 17,
      week: 17,
      divisions: ['None'],
      currentDivision: 'None'
    };
  }

  componentDidMount() {
    axios.get('/api/2017/divisions')
      .then(res => {
        this.setState({divisions: this.state.divisions.concat(res.data)});
      });
  }

  setYear(e) {
    this.setState({year: e.target.value});
  }

  setWeek(e) {
    this.setState({week: e.target.value});
  }

  getCurrentYear() {
    const date = new Date();
    return date.getFullYear();
  }

  getYearsArray() {
    const array = [];
    for (let i = 2002; i <= this.getCurrentYear(); i++) {
      array.push(i);
    }
    return array;
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
    const yearsArray = this.getYearsArray();
    const yearSelectOptions = yearsArray.map((e) =>
      <option key={e} value={e}>{e}</option>
    );

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
          <span>Year:</span>
          <select id='first-selector' defaultValue={this.state.year} onChange={this.setYear.bind(this)}>{yearSelectOptions}</select>

          <span>Week:</span>
          <select defaultValue={this.state.week} onChange={this.setWeek.bind(this)}>{weekSelectOptions}</select>

          <span>Division:</span>
          <select defaultValue={this.state.currentDivision} onChange={this.setDivision.bind(this)}>{divisionSelectOptions}</select>
        </div>

        <h2>SelfBased</h2>
        <Ranking name={'selfbased'} year={this.state.year} week={this.state.week} divisionString={this.state.currentDivision} />

        <h2>EvenPlay</h2>
        <Ranking name={'evenplay'} year={this.state.year} week={this.state.week} divisionString={this.state.currentDivision} />

        <h2>AdjustedWins</h2>
        <Ranking name={'adjustedwins'} year={this.state.year} week={this.state.week} divisionString={this.state.currentDivision} />
      </div>
    );
  }
}

render(<App/>, document.getElementById('app'));
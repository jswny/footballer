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
      divisions: null,
      currentDivision: null
    };

    axios.get('/api/divisions')
      .then(res => {
        this.setState({divisions: res.data});
        // this.populateDivisions(res.data);
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
    console.log(e.target.value);
    this.setState({currentDivision: e.target.value});
  }

  render () {
    const weeksArray = this.getWeeksArray();
    const weekSelectOptions = weeksArray.map((e) =>
      <option key={e} value={e}>{e}</option>
    );

    let divisions = this.state.divisions;
    let divisionSelectOptions = null;
    if (divisions != null) {
      console.log(divisions);
      divisionSelectOptions = divisions.map((e) =>
        <option key={e} value={e}>{e}</option>
      );
    }

    return (
      <div>
        <h1>Footballer</h1>

        <div>
          Week:
          <select defaultValue={this.state.week} onChange={this.setWeek.bind(this)}>{weekSelectOptions}</select>
        </div>

        <div>
          Division:
          <select defaultValue={this.state.currentDivision} onChange={this.setDivision.bind(this)}>{divisionSelectOptions}</select>
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
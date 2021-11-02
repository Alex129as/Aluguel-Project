import React from 'react';
import logo from './logo.svg';
import axios from 'axios';

import './App.css';

const  App: React.FunctionComponent = () => {
  
  const loadUsers = async () => {
    const myHeaders = new Headers({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Headers': 'Origin, X-Request-Width, Content-Type, Accept'
    });
    
    const response = await axios.get("http://localhost:8080/users/get/all/users", {
      
    headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Origin, X-Request-Width, Content-Type, Accept'
      },

      data: {
         username: "Emanuel",
	       id: "b2cd6d4e-a272-4549-bdb4-05c76ad4e82e",
      }
    });

    console.log(response.data);
  
  }

  loadUsers();

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;

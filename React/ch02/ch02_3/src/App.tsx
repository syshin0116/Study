import { Component} from 'react';

// export default class App extends Component {
//   render(){
//     const isLoading = true
//     if (isLoading) return <p>loading...</p>
//     return (
//       <ul>
//         <li>
//           <a href='http://www.google.com'>
//             <p>go to Google</p>
//           </a>
//         </li>
//       </ul>
//     )
//   }
// }

// export default class App extends Component {
//   render(){
//     const isLoading = true
//     const children = (
//       <li>
//         <a href='http://www.google.com'>
//           <p>go to Google</p>
//         </a>
//       </li>
//     )
//     return(
//       <div>
//         {isLoading && <p>loading...</p>}
//         {!isLoading && <ul>{children}</ul>}
//       </div>
//     )
//   }
// }

// export default class App extends Component{
//   render(){
//       const isLoading = true
//       const children = isLoading ? (
//         <p>loading...</p>
//       ) : (
//         <ul>
//           <li>
//             <a href='http://www.google.com'>
//               <p>go to Google</p>
//             </a>
//           </li>
//         </ul>
//       )
//       return <div>{children}</div>
//   }
// }

import ClassComponent from './ClassComponent'

export default class App extends Component{
  render(){
    return(
      <ul>
        <ClassComponent />
        <ClassComponent />
      </ul>
    )
  }
}
// import React from 'react';
import ReactDOM from 'react-dom/client';

// const CE = React.createElement

// const rootVirtualDOM = CE('ul', null, [
//   CE('li', null, [
//     CE('a', {href: 'http://www.google.com', target: '_blank'}, [
//       CE('p', null, 'go to google')
//     ])
//   ])
// ])

// const rootVirtualDOM=(
//   <ul>
//     <li>
//       <a href='http://www.google.com' target='_blank'>
//         <p>go to Google</p>
//       </a>
//     </li>
//   </ul>
// )
// const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement)
// root.render(rootVirtualDOM)

// const children = [
//   <li>
//     <a href='http://www.google.com' target='_blank'>
//       <p>go to Google</p>
//     </a>
//   </li>,
//   <li>
//     <a href='http://www.facebook.com' target='_blank'>
//       <p>go to Facebook</p>
//     </a>
//   </li>,
//   <li>
//     <a href='http://www.twitter.com' target='_blank'>
//       <p>go to Twitter</p>
//     </a>
//   </li>
// ]
// const rootVirtualDOM = <ul>{children}</ul>

// const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement)
// root.render(rootVirtualDOM)

const children = [0, 1, 2].map((n: number)=> <h3>Hello world! {n}</h3>)
const rootVirtualDOM = <div>{children}</div>

const root = ReactDOM. createRoot (document.getElementById('root') as HTMLElement)
root.render (rootVirtualDOM)
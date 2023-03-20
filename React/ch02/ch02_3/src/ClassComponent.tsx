import {Component, ReactNode} from 'react'
// export default class ClassComponent extends Component{
//     render(){
//         return(
//             <li>
              // <a href='http://www.google.com'>
              //   <p>go to Google</p>
              // </a>
//             </li>
//         )
//     }
// }

export type ClassComponentProps = {
  href: string
  text: string
}
export default class ClassComponent extends Component<ClassComponentProps>{
  render(){
      const {href, text} = this.props
      return(
        <li>
          <a href={href}>
            <p>{text}</p>
          </a>
        </li>
      )
  }
}
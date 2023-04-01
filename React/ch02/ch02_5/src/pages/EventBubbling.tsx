import { ECDH } from "crypto"
import type { SyntheticEvent } from "react"
export default function EventBubbling(){
    const onDivClick = (e: SyntheticEvent)=>{
        const {isTrusted, target, bubbles, currentTarget} = e
        console.log('click evnet bubles on <div>', isTrusted, target, bubbles, currentTarget)
    }
    const onButtonClick = (e:SyntheticEvent)=>{
        const {isTrusted, target, bubbles} = e
        console.log('click event starts at <button>', isTrusted, target, bubbles)
    }
    return (
    <div onClick={onDivClick}>
        <p>EvnetBubbling</p>
        <button onClick={onButtonClick}>Click Me</button>
    </div>
    )
}
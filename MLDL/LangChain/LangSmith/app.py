from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser

import os
from dotenv import load_dotenv, find_dotenv

load_dotenv(find_dotenv())

os.environ["OPENAI_API_KEY"] = os.getenv("OPENAI_API_KEY")
os.environ["LANGCHAIN_TRACING_V2"] = "true"
os.environ["LANGCHAIN_API_KEY"] = os.getenv("LANGCHAIN_API_KEY")

prompt = ChatPromptTemplate.from_messages(
    [
        ("system", "You are a helpful assistant. Please response to the user request only based on the given context"),
        ("user", "Question:{question}\nContext:{context}")
    ]
)

model = ChatOpenAI(model="gpt-3.5-turbo")
output_parser = StrOutputParser()

chain = prompt|model|output_parser
question = "Can you summarize the speech?"
context = """As far as black Americans were concerned, the nation’s response to Brown was agonizingly slow, and neither state legislatures nor the Congress seemed willing to help their cause along. Finally, President John F. Kennedy recognized that only a strong civil rights bill would put teeth into the drive to secure equal protection of the laws for African Americans. On June 11, 1963, he proposed such a bill to Congress, asking for legislation that would provide “the kind of equality of treatment which we would want for ourselves.” Southern representatives in Congress managed to block the bill in committee, and civil rights leaders sought some way to build political momentum behind the measure.

A. Philip Randolph, a labor leader and longtime civil rights activist, called for a massive march on Washington to dramatize the issue. He welcomed the participation of white groups as well as black in order to demonstrate the multiracial backing for civil rights. The various elements of the civil rights movement, many of which had been wary of one another, agreed to participate. The National Association for the Advancement of Colored People, the Congress of Racial Equality, the Southern Christian Leadership Conference, the Student Non-violent Coordinating Committee and the Urban League all managed to bury their differences and work together. The leaders even agreed to tone down the rhetoric of some of the more militant activists for the sake of unity, and they worked closely with the Kennedy administration, which hoped the march would, in fact, lead to passage of the civil rights bill.

On August 28, 1963, under a nearly cloudless sky, more than 250,000 people, a fifth of them white, gathered near the Lincoln Memorial in Washington to rally for “jobs and freedom.” The roster of speakers included speakers from nearly every segment of society — labor leaders like Walter Reuther, clergy, film stars such as Sidney Poitier and Marlon Brando and folksingers such as Joan Baez. Each of the speakers was allotted fifteen minutes, but the day belonged to the young and charismatic leader of the Southern Christian Leadership Conference.

Dr. Martin Luther King Jr. had originally prepared a short and somewhat formal recitation of the sufferings of African Americans attempting to realize their freedom in a society chained by discrimination. He was about to sit down when gospel singer Mahalia Jackson called out, “Tell them about your dream, Martin! Tell them about the dream!” Encouraged by shouts from the audience, King drew upon some of his past talks, and the result became the landmark statement of civil rights in America — a dream of all people, of all races and colors and backgrounds, sharing in an America marked by freedom and democracy.

For further reading: Herbert Garfinkel, When Negroes March: The March on Washington…(1969); Taylor Branch, Parting the Waters: America in the King Years, 1954-1963 (1988); Stephen B. Oates, Let the Trumpet Sound: The Life of Martin Luther King Jr. (1982).
“I HAVE A DREAM” (1963)

I am happy to join with you today in what will go down in history as the greatest demonstration for freedom in the history of our nation.

Five score years ago, a great American, in whose symbolic shadow we stand today, signed the Emancipation Proclamation. This momentous decree came as a great beacon of hope to millions of slaves, who had been seared in the flames of whithering injustice. It came as a joyous daybreak to end the long night of their captivity. But one hundred years later, the colored America is still not free. One hundred years later, the life of the colored American is still sadly crippled by the manacle of segregation and the chains of discrimination.

One hundred years later, the colored American lives on a lonely island of poverty in the midst of a vast ocean of material prosperity. One hundred years later, the colored American is still languishing in the corners of American society and finds himself an exile in his own land So we have come here today to dramatize a shameful condition.

In a sense we have come to our Nation’s Capital to cash a check. When the architects of our great republic wrote the magnificent words of the Constitution and the Declaration of Independence, they were signing a promissory note to which every American was to fall heir.

This note was a promise that all men, yes, black men as well as white men, would be guaranteed the inalienable rights of life liberty and the pursuit of happiness.

It is obvious today that America has defaulted on this promissory note insofar as her citizens of color are concerned. Instead of honoring this sacred obligation, America has given its colored people a bad check, a check that has come back marked “insufficient funds.”

But we refuse to believe that the bank of justice is bankrupt. We refuse to believe that there are insufficient funds in the great vaults of opportunity of this nation. So we have come to cash this check, a check that will give us upon demand the riches of freedom and security of justice.

We have also come to his hallowed spot to remind America of the fierce urgency of Now. This is not time to engage in the luxury of cooling off or to take the tranquilizing drug of gradualism.

Now is the time to make real the promise of democracy.

Now it the time to rise from the dark and desolate valley of segregation to the sunlit path of racial justice.

Now it the time to lift our nation from the quicksand of racial injustice to the solid rock of brotherhood.

Now is the time to make justice a reality to all of God’s children.

I would be fatal for the nation to overlook the urgency of the moment and to underestimate the determination of it’s colored citizens. This sweltering summer of the colored people’s legitimate discontent will not pass until there is an invigorating autumn of freedom and equality. Nineteen sixty-three is not an end but a beginning. Those who hope that the colored Americans needed to blow off steam and will now be content will have a rude awakening if the nation returns to business as usual.

There will be neither rest nor tranquility in America until the colored citizen is granted his citizenship rights. The whirlwinds of revolt will continue to shake the foundations of our nation until the bright day of justice emerges.

We can never be satisfied as long as our bodies, heavy with the fatigue of travel, cannot gain lodging in the motels of the highways and the hotels of the cities.

We cannot be satisfied as long as the colored person’s basic mobility is from a smaller ghetto to a larger one.

We can never be satisfied as long as our children are stripped of their selfhood and robbed of their dignity by signs stating “for white only.”

We cannot be satisfied as long as a colored person in Mississippi cannot vote and a colored person in New York believes he has nothing for which to vote.

No, no we are not satisfied and we will not be satisfied until justice rolls down like waters and righteousness like a mighty stream.

I am not unmindful that some of you have come here out of your trials and tribulations. Some of you have come from areas where your quest for freedom left you battered by storms of persecutions and staggered by the winds of police brutality.

You have been the veterans of creative suffering. Continue to work with the faith that unearned suffering is redemptive.

Go back to Mississippi, go back to Alabama, go back to South Carolina go back to Georgia, go back to Louisiana, go back to the slums and ghettos of our modern cities, knowing that somehow this situation can and will be changed.

Let us not wallow in the valley of despair. I say to you, my friends, we have the difficulties of today and tomorrow.

I still have a dream. It is a dream deeply rooted in the American dream.

I have a dream that one day this nation will rise up and live out the true meaning of its creed. We hold these truths to be self-evident that all men are created equal.

I have a dream that one day out in the red hills of Georgia the sons of former slaves and the sons of former slaveowners will be able to sit down together at the table of brotherhood.

I have a dream that one day even the state of Mississippi, a state sweltering with the heat of oppression, will be transformed into an oasis of freedom and justice.

I have a dream that my four little children will one day live in a nation where they will not be judged by the color of their skin but by their character.

I have a dream today.

I have a dream that one day down in Alabama, with its vicious racists, with its governor having his lips dripping with the words of interposition and nullification; that one day right down in Alabama little black boys and black girls will be able to join hands with little white boys and white girls as sisters and brothers.

I have a dream today.

I have a dream that one day every valley shall be engulfed, every hill shall be exalted and every mountain shall be made low, the rough places will be made plains and the crooked places will be made straight and the glory of the Lord shall be revealed and all flesh shall see it together.

This is our hope. This is the faith that I will go back to the South with. With this faith we will be able to hew out of the mountain of despair a stone of hope.

With this faith we will be able to transform the jangling discords of our nation into a beautiful symphony of brotherhood.

With this faith we will be able to work together, to pray together, to struggle together, to go to jail together, to climb up for freedom together, knowing that we will be free one day.

This will be the day when all of God’s children will be able to sing with new meaning “My country ’tis of thee, sweet land of liberty, of thee I sing. Land where my father’s died, land of the Pilgrim’s pride, from every mountainside, let freedom ring!”

And if America is to be a great nation, this must become true. So let freedom ring from the hilltops of New Hampshire. Let freedom ring from the mighty mountains of New York.

Let freedom ring from the heightening Alleghenies of Pennsylvania.

Let freedom ring from the snow-capped Rockies of Colorado.

Let freedom ring from the curvaceous slopes of California.

But not only that, let freedom, ring from Stone Mountain of Georgia.

Let freedom ring from every hill and molehill of Mississippi and every mountainside.

When we let freedom ring, when we let it ring from every tenement and every hamlet, from every state and every city, we will be able to speed up that day when all of God’s children, black men and white men, Jews and Gentiles, Protestants and Catholics, will be able to join hands and sing in the words of the old spiritual, “Free at last, free at last. Thank God Almighty, we are free at last.”
"""

print(chain.invoke({"question":question, "context":context}))
(ns text-cluster.experiments
  (:require [text-cluster.clustering :refer [cluster-words-in-text]]))

;; Suspicious Minds - Elvis Presley
(comment (cluster-words-in-text
          "We're caught in a trap
I can't walk out
Because I love you too much baby

Why can't you see
What you're doing to me
When you don't believe a word I say?

We can't go on together
With suspicious minds
And we can't build our dreams
On suspicious minds

So, if an old friend I know
Drops by to say hello
Would I still see suspicion in your eyes?

Here we go again
Asking where I've been
You can't see these tears are real
I'm crying

We can't go on together
With suspicious minds
And we can't build our dreams
On suspicious minds

Oh let our love survive
Or dry the tears from your eyes
Let's don't let a good thing die

When honey, you know
I've never lied to you
Mmm yeah, yeah "
          "en"
          "/home/josep/ADM/Paper3/results/suspicious_minds.png"
          "/home/josep/ADM/Paper3/results/suspicious_minds_dist.csv"
          "/home/josep/ADM/Paper3/results/suspicious_minds.tex")

         ;; Keelhauled - Alestorm 
         (cluster-words-in-text
          "My friends I stand before you
To tell a truth most dire
There lurks a traitor in our midst
Who hath invoked the captain's ire

He don't deserve no mercy
We ought to shoot him with a gun
But I am not an evil man
So first let's have a little fun

We'll tie that scoundrel to a rope
And throw him overboard
Drag him underneath the ship
A terrifying deadly trip

Keelhaul that filthy landlubber
Send him down to the depths below
Make that bastard walk the plank
With a bottle of rum and a yo ho ho

I will not say what he has done
His sins are far too grave to tell
It's not my place to judge a man
But for them he will burn in hell

The sharks will dine upon his flesh
And Davy Jones will have his soul
Take his money and his hat
He won't need them where he's gonna go

But first lets tie him to a rope
And throw him overboard
Drag him underneath the ship
A terrifying deadly trip

Keelhaul that filthy landlubber
Send him down to the depths below
Make that bastard walk the plank
With a bottle of rum and a yo ho ho "
          "en"
          "/home/josep/ADM/Paper3/results/keelhauled.png"
          "/home/josep/ADM/Paper3/results/keelhauled_dist.csv"
          "/home/josep/ADM/Paper3/results/keelhauled.tex")

         (cluster-words-in-text
          "Hi, Barbie
Hi, Ken!
Do you wanna go for a ride?
Sure, Ken!
Jump in...

I'm a Barbie girl in the Barbie world
Life in plastic, it's fantastic!
You can brush my hair, undress me everywhere
Imagination, life is your creation
Come on, Barbie, let's go party!

I'm a Barbie girl in the Barbie world
Life in plastic, it's fantastic!
You can brush my hair, undress me everywhere
Imagination, life is your creation

I'm a blond bimbo girl in a fantasy world
Dress me up, make it tight, I'm your dolly
You're my doll, rock'n'roll, feel the glamour in pink,
Kiss me here, touch me there, hanky panky...
You can touch, you can play, if you say, \"I'm always yours.\"

(uu-oooh-u) [2x]

I'm a Barbie girl in the Barbie world
Life in plastic, it's fantastic!
You can brush my hair, undress me everywhere
Imagination, life is your creation

Come on, Barbie, let's go party!
(ah-ah-ah-yeah)
Come on, Barbie, let's go party!
(uu-oooh-u) [2x]
Come on, Barbie, let's go party!
(ah-ah-ah-yeah)
Come on, Barbie, let's go party!
(uu-oooh-u) [2x]

Make me walk, make me talk, do whatever you please
I can act like a star, I can beg on my knees
Come jump in, bimbo friend, let us do it again,
Hit the town, fool around, let's go party
You can touch, you can play, if you say, \"I'm always yours.\"
You can touch, you can play, if you say, \"I'm always yours.\"

Come on, Barbie, let's go party!
(ah-ah-ah-yeah)
Come on, Barbie, let's go party!
(uu-oooh-u) [2x]
Come on, Barbie, let's go party!
(ah-ah-ah-yeah)
Come on, Barbie, let's go party!
(uu-oooh-u) [2x]

I'm a Barbie girl in the Barbie world
Life in plastic, it's fantastic!
You can brush my hair, undress me everywhere
Imagination, life is your creation

I'm a Barbie girl in the Barbie world
Life in plastic, it's fantastic!
You can brush my hair, undress me everywhere
Imagination, life is your creation

Come on, Barbie, let's go party!
(Ah-ah-ah-yeah)
Come on, Barbie, let's go party!
(uu-oooh-u) [2x]
Come on, Barbie, let's go party!
(ah-ah-ah-yeah)
Come on, Barbie, let's go party!
(uu-oooh-u) [2x]

Oh, I'm having so much fun!
Well, Barbie, we're just getting started
Oh, I love you, Ken! "
          "en"
          "/home/josep/ADM/Paper3/results/barbie.png"
          "/home/josep/ADM/Paper3/results/barbie_dist.csv"
          "/home/josep/ADM/Paper3/results/barbie.tex")

         (cluster-words-in-text
          "Start spreading the news, I'm leaving today.
I want to be a part of it, New York, New York.
These vagabond shoes, are longing to stray 
Right through the very heart of it, New York, New York.

I wanna wake up, In a city that doesn't sleep.
And find I'm king of the hill, top of the heap.

These little town blues, are melting away.
I'll make a brand new start of it, in old New York.

If I can make it there, 
I'll make it anywhere.
It's up to you, New York, New York.

New York, New York.
I want to wake up, in a city that never sleeps.
And find I'm A-number-one, top of the list, king of the hill, A-number-1... 

These little town blues, are melting away.
I'm gonna make a brand new start of it, 
In old New York, and... 

If I can make it there, I'm gone make it anywhere.
It's up to you, New York, New York! "
          "en"
          "/home/josep/ADM/Paper3/results/newyork.png"
          "/home/josep/ADM/Paper3/results/newyork_dist.csv"
          "/home/josep/ADM/Paper3/results/newyork.tex")

         ;; Bohemian Rhapsody - Queen
         (cluster-words-in-text
          "Is this the real life? Is this just fantasy? Caught in a landslide, No escape from reality. Open your eyes, Look up to the skies and see, I'm just a poor boy, I need no sympathy, Because I'm easy come, easy go, Little high, little low, Any way the wind blows doesn't really matter to me, to me. Mama, just killed a man, Put a gun against his head, Pulled my trigger, now he's dead. Mama, life had just begun, But now I've gone and thrown it all away. Mama, ooh, Didn't mean to make you cry, If I'm not back again this time tomorrow, Carry on, carry on as if nothing really matters. Too late, my time has come, Sends shivers down my spine, Body's aching all the time. Goodbye, everybody, I've got to go, Gotta leave you all behind and face the truth. Mama, ooh (any way the wind blows), I don't wanna die, I sometimes wish I'd never been born at all. I see a little silhouetto of a man, Scaramouche, Scaramouche, will you do the Fandango? Thunderbolt and lightning, Very, very frightening me. (Galileo) Galileo. (Galileo) Galileo, Galileo Figaro Magnifico. I'm just a poor boy, nobody loves me. He's just a poor boy from a poor family, Spare him his life from this monstrosity. Easy come, easy go, will you let me go? Bismillah! No, we will not let you go. (Let him go!) Bismillah! We will not let you go. (Let him go!) Bismillah! We will not let you go. (Let me go!) Will not let you go. (Let me go!) Never, never let you go Never let me go, oh. No, no, no, no, no, no, no. Oh, mama mia, mama mia (Mama mia, let me go.) Beelzebub has a devil put aside for me, for me, for me. So you think you can stone me and spit in my eye? So you think you can love me and leave me to die? Oh, baby, can't do this to me, baby, Just gotta get out, just gotta get right outta here. (Ooooh, ooh yeah, ooh yeah) Nothing really matters, Anyone can see, Nothing really matters, Nothing really matters to me. Any way the wind blows."
          "en"
          "/home/josep/ADM/Paper3/results/bohemian_rhapsody.png"
          "/home/josep/ADM/Paper3/results/bohemian_rhapsody_dist.csv"
          "/home/josep/ADM/Paper3/results/bohemian_rhapsody.tex"
          ))

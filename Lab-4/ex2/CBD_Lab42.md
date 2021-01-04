# Exercício 4.2

1
: Encontre todos os atores que dirigiram um filme em que também atuaram e apresente o nome do ator e o título do filme.

```
MATCH(actor) -[:DIRECTED] -> (movie) <- [:ACTED_IN] - (actor) 
RETURN actor.name, movie.title
╒════════════════╤═══════════════════╕
│"actor.name"    │"movie.title"      │
╞════════════════╪═══════════════════╡
│"Tom Hanks"     │"That Thing You Do"│
├────────────────┼───────────────────┤
│"Clint Eastwood"│"Unforgiven"       │
├────────────────┼───────────────────┤
│"Danny DeVito"  │"Hoffa"            │
└────────────────┴───────────────────┘
```

2
: Para cada filme realizado depois de 2005, apresente os nomes de todos os atores que atuaram nesse filme.

```
MATCH(actor) -[:ACTED_IN] -> (movie) WHERE movie.released > 2005
Return DISTINCT movie.title, actor.name
╒══════════════════════╤════════════════════════╕
│"movie.title"         │"actor.name"            │
╞══════════════════════╪════════════════════════╡
│"RescueDawn"          │"Zach Grenier"          │
├──────────────────────┼────────────────────────┤
│"RescueDawn"          │"Steve Zahn"            │
├──────────────────────┼────────────────────────┤
│"RescueDawn"          │"Christian Bale"        │
├──────────────────────┼────────────────────────┤
│"RescueDawn"          │"Marshall Bell"         │
├──────────────────────┼────────────────────────┤
│"Cloud Atlas"         │"Tom Hanks"             │
├──────────────────────┼────────────────────────┤
│"Cloud Atlas"         │"Jim Broadbent"         │
├──────────────────────┼────────────────────────┤
│"Cloud Atlas"         │"Halle Berry"           │
├──────────────────────┼────────────────────────┤
│"Cloud Atlas"         │"Hugo Weaving"          │
├──────────────────────┼────────────────────────┤
│"The Da Vinci Code"   │"Audrey Tautou"         │
├──────────────────────┼────────────────────────┤
│"The Da Vinci Code"   │"Tom Hanks"             │
├──────────────────────┼────────────────────────┤
│"The Da Vinci Code"   │"Ian McKellen"          │
├──────────────────────┼────────────────────────┤
│"The Da Vinci Code"   │"Paul Bettany"          │
├──────────────────────┼────────────────────────┤
│"V for Vendetta"      │"Ben Miles"             │
├──────────────────────┼────────────────────────┤
│"V for Vendetta"      │"Natalie Portman"       │
├──────────────────────┼────────────────────────┤
│"V for Vendetta"      │"John Hurt"             │
├──────────────────────┼────────────────────────┤
│"V for Vendetta"      │"Stephen Rea"           │
├──────────────────────┼────────────────────────┤
│"V for Vendetta"      │"Hugo Weaving"          │
├──────────────────────┼────────────────────────┤
│"Speed Racer"         │"Susan Sarandon"        │
├──────────────────────┼────────────────────────┤
│"Speed Racer"         │"Ben Miles"             │
├──────────────────────┼────────────────────────┤
│"Speed Racer"         │"John Goodman"          │
├──────────────────────┼────────────────────────┤
│"Speed Racer"         │"Christina Ricci"       │
├──────────────────────┼────────────────────────┤
│"Speed Racer"         │"Matthew Fox"           │
├──────────────────────┼────────────────────────┤
│"Speed Racer"         │"Rain"                  │
├──────────────────────┼────────────────────────┤
│"Speed Racer"         │"Emile Hirsch"          │
├──────────────────────┼────────────────────────┤
│"Ninja Assassin"      │"Naomie Harris"         │
├──────────────────────┼────────────────────────┤
│"Ninja Assassin"      │"Rain"                  │
├──────────────────────┼────────────────────────┤
│"Ninja Assassin"      │"Rick Yune"             │
├──────────────────────┼────────────────────────┤
│"Ninja Assassin"      │"Ben Miles"             │
├──────────────────────┼────────────────────────┤
│"Frost/Nixon"         │"Oliver Platt"          │
├──────────────────────┼────────────────────────┤
│"Frost/Nixon"         │"Frank Langella"        │
├──────────────────────┼────────────────────────┤
│"Frost/Nixon"         │"Kevin Bacon"           │
├──────────────────────┼────────────────────────┤
│"Frost/Nixon"         │"Michael Sheen"         │
├──────────────────────┼────────────────────────┤
│"Frost/Nixon"         │"Sam Rockwell"          │
├──────────────────────┼────────────────────────┤
│"Charlie Wilson's War"│"Tom Hanks"             │
├──────────────────────┼────────────────────────┤
│"Charlie Wilson's War"│"Julia Roberts"         │
├──────────────────────┼────────────────────────┤
│"Charlie Wilson's War"│"Philip Seymour Hoffman"│
├──────────────────────┼────────────────────────┤
│"Parasite"            │"Kang-ho Song"          │
├──────────────────────┼────────────────────────┤
│"Parasite"            │"Yeo-jeong Jo"          │
├──────────────────────┼────────────────────────┤
│"Parasite"            │"Sun-kyun Lee"          │
├──────────────────────┼────────────────────────┤
│"Parasite"            │"So-dam Park"           │
├──────────────────────┼────────────────────────┤
│"Parasite"            │"Woo-sik Choi"          │
├──────────────────────┼────────────────────────┤
│"Joker"               │"Robert De Niro"        │
├──────────────────────┼────────────────────────┤
│"Joker"               │"Zazie Beetz"           │
├──────────────────────┼────────────────────────┤
│"Joker"               │"Joaquin Phoenix"       │
└──────────────────────┴────────────────────────┘
```

3
: Encontre pares de nós com mais do que uma relação entre si.

```
MATCH (n)-[r]-(m) 
WITH n,m, count(r) as num_links
WHERE num_links > 1
RETURN DISTINCT n.title, m.name, num_links
╒════════════════════════╤════════════════╤═══════════╕
│"n.title"               │"m.name"        │"num_links"│
╞════════════════════════╪════════════════╪═══════════╡
│"Speed Racer"           │"Andy Wachowski"│2          │
├────────────────────────┼────────────────┼───────────┤
│"V for Vendetta"        │"Andy Wachowski"│2          │
├────────────────────────┼────────────────┼───────────┤
│"Speed Racer"           │"Lana Wachowski"│2          │
├────────────────────────┼────────────────┼───────────┤
│"V for Vendetta"        │"Lana Wachowski"│2          │
├────────────────────────┼────────────────┼───────────┤
│null                    │null            │2          │
├────────────────────────┼────────────────┼───────────┤
│"When Harry Met Sally"  │"Rob Reiner"    │2          │
├────────────────────────┼────────────────┼───────────┤
│"A Few Good Men"        │"Aaron Sorkin"  │2          │
├────────────────────────┼────────────────┼───────────┤
│null                    │null            │3          │
├────────────────────────┼────────────────┼───────────┤
│"Jerry Maguire"         │"Cameron Crowe" │3          │
├────────────────────────┼────────────────┼───────────┤
│"That Thing You Do"     │"Tom Hanks"     │2          │
├────────────────────────┼────────────────┼───────────┤
│"When Harry Met Sally"  │"Nora Ephron"   │2          │
├────────────────────────┼────────────────┼───────────┤
│"Unforgiven"            │"Clint Eastwood"│2          │
├────────────────────────┼────────────────┼───────────┤
│"Hoffa"                 │"Danny DeVito"  │2          │
├────────────────────────┼────────────────┼───────────┤
│"Something's Gotta Give"│"Nancy Meyers"  │3          │
└────────────────────────┴────────────────┴───────────┘

Aqui apercebi-me que devia meter DISTINCT em alguns returns. Fui atrás e alterei.
```
 
4
: Encontre todos os pares de pessoas que fizeram revisões do mesmo filme. Apresente os seus nomes e título de cada filme.

```
Match(p1:Person) -[:REVIEWED] - (m:Movie) - [:REVIEWED] - (p2:Person)
Return DISTINCT p1.name, p2.name, m.title
╒══════════════════╤══════════════════╤═══════════════════╕
│"p1.name"         │"p2.name"         │"m.title"          │
╞══════════════════╪══════════════════╪═══════════════════╡
│"James Thompson"  │"Angela Scope"    │"The Replacements" │
├──────────────────┼──────────────────┼───────────────────┤
│"Jessica Thompson"│"Angela Scope"    │"The Replacements" │
├──────────────────┼──────────────────┼───────────────────┤
│"Angela Scope"    │"James Thompson"  │"The Replacements" │
├──────────────────┼──────────────────┼───────────────────┤
│"Jessica Thompson"│"James Thompson"  │"The Replacements" │
├──────────────────┼──────────────────┼───────────────────┤
│"Angela Scope"    │"Jessica Thompson"│"The Replacements" │
├──────────────────┼──────────────────┼───────────────────┤
│"James Thompson"  │"Jessica Thompson"│"The Replacements" │
├──────────────────┼──────────────────┼───────────────────┤
│"Jessica Thompson"│"James Thompson"  │"The Da Vinci Code"│
├──────────────────┼──────────────────┼───────────────────┤
│"James Thompson"  │"Jessica Thompson"│"The Da Vinci Code"│
└──────────────────┴──────────────────┴───────────────────┘
```

5
: Encontre todos os pares de atores que atuaram em vários filmes juntos.

```
MATCH (a1:Person)-[:ACTED_IN]->(m:Movie)<-[:ACTED_IN]-(a2:Person) 
WITH a1,a2, count(m) as num_movies
WHERE num_movies > 1 
RETURN DISTINCT a1.name,a2.name, num_movies
╒════════════════════╤════════════════════╤════════════╕
│"a1.name"           │"a2.name"           │"num_movies"│
╞════════════════════╪════════════════════╪════════════╡
│"Laurence Fishburne"│"Hugo Weaving"      │3           │
├────────────────────┼────────────────────┼────────────┤
│"Carrie-Anne Moss"  │"Hugo Weaving"      │3           │
├────────────────────┼────────────────────┼────────────┤
│"Keanu Reeves"      │"Hugo Weaving"      │3           │
├────────────────────┼────────────────────┼────────────┤
│"Hugo Weaving"      │"Laurence Fishburne"│3           │
├────────────────────┼────────────────────┼────────────┤
│"Carrie-Anne Moss"  │"Laurence Fishburne"│3           │
├────────────────────┼────────────────────┼────────────┤
│"Keanu Reeves"      │"Laurence Fishburne"│3           │
├────────────────────┼────────────────────┼────────────┤
│"Hugo Weaving"      │"Carrie-Anne Moss"  │3           │
├────────────────────┼────────────────────┼────────────┤
│"Laurence Fishburne"│"Carrie-Anne Moss"  │3           │
├────────────────────┼────────────────────┼────────────┤
│"Keanu Reeves"      │"Carrie-Anne Moss"  │3           │
├────────────────────┼────────────────────┼────────────┤
│"Hugo Weaving"      │"Keanu Reeves"      │3           │
├────────────────────┼────────────────────┼────────────┤
│"Laurence Fishburne"│"Keanu Reeves"      │3           │
├────────────────────┼────────────────────┼────────────┤
│"Carrie-Anne Moss"  │"Keanu Reeves"      │3           │
├────────────────────┼────────────────────┼────────────┤
│"Jack Nicholson"    │"J.T. Walsh"        │2           │
├────────────────────┼────────────────────┼────────────┤
│"Jack Nicholson"    │"Cuba Gooding Jr."  │2           │
├────────────────────┼────────────────────┼────────────┤
│"Tom Cruise"        │"Cuba Gooding Jr."  │2           │
├────────────────────┼────────────────────┼────────────┤
│"J.T. Walsh"        │"Jack Nicholson"    │2           │
├────────────────────┼────────────────────┼────────────┤
│"Cuba Gooding Jr."  │"Jack Nicholson"    │2           │
├────────────────────┼────────────────────┼────────────┤
│"Cuba Gooding Jr."  │"Tom Cruise"        │2           │
├────────────────────┼────────────────────┼────────────┤
│"Meg Ryan"          │"Tom Hanks"         │3           │
├────────────────────┼────────────────────┼────────────┤
│"Tom Hanks"         │"Meg Ryan"          │3           │
├────────────────────┼────────────────────┼────────────┤
│"Rosie O'Donnell"   │"Tom Hanks"         │2           │
├────────────────────┼────────────────────┼────────────┤
│"Tom Hanks"         │"Rosie O'Donnell"   │2           │
├────────────────────┼────────────────────┼────────────┤
│"Rain"              │"Ben Miles"         │2           │
├────────────────────┼────────────────────┼────────────┤
│"Ben Miles"         │"Rain"              │2           │
├────────────────────┼────────────────────┼────────────┤
│"Tom Hanks"         │"Gary Sinise"       │2           │
├────────────────────┼────────────────────┼────────────┤
│"Gary Sinise"       │"Tom Hanks"         │2           │
├────────────────────┼────────────────────┼────────────┤
│"Danny DeVito"      │"Jack Nicholson"    │2           │
├────────────────────┼────────────────────┼────────────┤
│"Jack Nicholson"    │"Danny DeVito"      │2           │
├────────────────────┼────────────────────┼────────────┤
│"Bill Paxton"       │"Tom Hanks"         │2           │
├────────────────────┼────────────────────┼────────────┤
│"Tom Hanks"         │"Bill Paxton"       │2           │
└────────────────────┴────────────────────┴────────────┘
```

6
: Determine a idade média do elenco do filme "Apollo 13" no ano do lançamento do filme

```
MATCH (actor)-[r:ACTED_IN]->(m:Movie)
WHERE m.title = "Apollo 13"
WITH date.realtime().year - round(avg(actor.born)) as avg_age
RETURN avg_age
╒═════════╕
│"avg_age"│
╞═════════╡
│66.0     │
└─────────┘
```

7
: Encontre os 10 filmes com o elenco mais velho no momento do lançamento do filme. Apresente o filme, a idade média arredondada a duas casas decimais, por ordem decrescente.  

```
MATCH (actor)-[r:ACTED_IN]->(m:Movie)
WITH m, date.realtime().year - round(avg(actor.born)) as avg_age
RETURN m.title, avg_age
ORDER BY avg_age DESC
LIMIT 10
╒═════════════════════════════════╤═════════╕
│"m.title"                        │"avg_age"│
╞═════════════════════════════════╪═════════╡
│"Joker"                          │null     │
├─────────────────────────────────┼─────────┤
│"Parasite"                       │null     │
├─────────────────────────────────┼─────────┤
│"Unforgiven"                     │91.0     │
├─────────────────────────────────┼─────────┤
│"One Flew Over the Cuckoo's Nest"│80.0     │
├─────────────────────────────────┼─────────┤
│"The Birdcage"                   │75.0     │
├─────────────────────────────────┼─────────┤
│"Hoffa"                          │74.0     │
├─────────────────────────────────┼─────────┤
│"Something's Gotta Give"         │72.0     │
├─────────────────────────────────┼─────────┤
│"What Dreams May Come"           │71.0     │
├─────────────────────────────────┼─────────┤
│"Snow Falling on Cedars"         │68.0     │
├─────────────────────────────────┼─────────┤
│"When Harry Met Sally"           │67.0     │
└─────────────────────────────────┴─────────┘

```

8
: Apresente o subgrafo ACTED_IN do filme com o elenco mais novo, no momento do lançamento do filme.  

```

```

9
: Qual é o caminho mais curto (usando qualquer tipo de relação) entre John Cusack e Demi Moore? 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```

4
: 

```
FALTA FAZER

```
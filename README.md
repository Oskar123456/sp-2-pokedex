# Java Web Project SP-2 "pokedex"

### Description

Titel: pokedex

url: pokedex.obhnothing.dk

En API til at hente information om pokemons, som f.eks. kunne bruges til et spil.
Med en backend der henter disse informationer fra https://pokeapi.co.

### Overview

#### API

| \multicolumn{1}{c}{	exttt{method}} | \multicolumn{1}{c}{url}   | \multicolumn{1}{c}{params} | \multicolumn{1}{c}{request body} | \multicolumn{1}{c}{response} | \multicolumn{1}{c}{error} |
|------------------------------------|---------------------------|----------------------------|----------------------------------|------------------------------|---------------------------|
| \texttt{GET}                       | \texttt{/api/pokemon}     | \texttt{id}                |                                  | \texttt{pokejson}            | \texttt{errorjson}        |
| \texttt{GET}                       | \texttt{/api/pokemon/all} |                            |                                  | \texttt{pokejson[]}          | \texttt{errorjson}        |
| \texttt{POST}                      | \texttt{/api/pokemon}     |                            | \texttt{pokejson}~(-- id)        | \texttt{pokejson}            | \texttt{errorjson}        |
| \texttt{PUT}                       | \texttt{/api/pokemon}     |                            | \texttt{pokejson}                | \texttt{pokejson}            | \texttt{errorjson}        |
| \texttt{DELETE}                    | \texttt{/api/pokemon}     | \texttt{id}                |                                  | \texttt{pokejson}            | \texttt{errorjson}        |

```GET pokedex.obhnothing.dk/api/pokemon?id=xxx:```

```
{
  id: Number
  name: String,
  base_experience: Number,
  height: Number,
  is_default: Boolean,
  order_number: Number
  weight: Number,
  location_area_encounters: String,
  hp: Number
  attack: Number
  defense: Number
  speed: Number
  specie_name: String,
  specie_url: String,
  sprite: {
    id: Number,
    back_default: String,
    back_female: String,
    back_shiny: String,
    back_shiny_female: String,
    front_default: String,
    front_female: String,
    front_shiny: String,
    front_shiny_female: String
  },
  types[]: {
      name: String,
      url: String
    }
  abilities[]: {
      name: String,
      url: String
    },
  forms[]: {
      name: String,
      url: String
    },
  moves[]: {
      name: String,
      url: String
    }
}
```

#### Entities

![uml](https://github.com/Oskar123456/sp-2-pokedex/blob/master/report/ERD.png?raw=true)

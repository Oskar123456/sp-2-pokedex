# Java Web Project SP-2 "pokedex"

### Description

Data can potentially be worth a lot of money. A common use case is to collect, store, and enrich related data from various sources (API’s, webpages etc) - and then expose the data in useful places. Sometimes new and interesting products can emerge from this.

In this exercise we will train our data collecting skills using JPA, DTOs, Java streams, and fetching data from REST API’s.

### Overview

#### API

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
  types: [
    {
      name: String,
      url: String
    }
  ],
  abilities: [
    {
      name: String,
      url: String
    },
  ],
  forms: [
    {
      name: String,
      url: String
    }
  ],
  moves: [
    {
      name: String,
      url: String
    },
    ]
}
```

#### Entities

![uml](https://github.com/Oskar123456/sp-2-pokedex/blob/master/report/ERD.png?raw=true)

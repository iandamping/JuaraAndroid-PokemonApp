package com.example.juaraandroid_pokemonapp.core.domain.model

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.*
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetailSpecies
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonStat
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_EGG_MONS
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_SKILL_MONS
import com.example.juaraandroid_pokemonapp.util.PokemonConstant.ONE_TYPE_MONS
import java.util.*

/**
 * Created by Ian Damping on 08,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
fun PokemonDetailResponse.mapToDetail(): PokemonDetail = PokemonDetail(
    pokemonId = pokemonId,
    pokemonWeight = pokemonWeight,
    pokemonHeight = pokemonHeight,
    pokemonName = pokemonName.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    },
    pokemonImage = pokemonImage.sprites.other.image,
    pokemonSmallImage1 = pokemonImage.smallImage1,
    pokemonSmallImage2 = pokemonImage.smallImage2,
    pokemonSmallImage3 = pokemonImage.smallImage3,
    pokemonSmallImage4 = pokemonImage.smallImage4,
    pokemonStat0 = pokemonStats[0].mapToDetail(),
    pokemonStat1 = pokemonStats[1].mapToDetail(),
    pokemonStat2 = pokemonStats[2].mapToDetail(),
    pokemonStat3 = pokemonStats[3].mapToDetail(),
    pokemonStat4 = pokemonStats[4].mapToDetail(),
    pokemonStat5 = pokemonStats[5].mapToDetail(),
    pokemonType0 = pokemonTypes[0].type.typeName.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    },
    pokemonType1 = pokemonTypes.checkTypeList(1, 1),
    pokemonAbility1 = pokemonAbilities[0].abilities.abilityName.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    },
    pokemonAbility2 = pokemonAbilities.checkAbilitiesList(1, 1),
    pokemonSpeciesUrl = pokemonSpecies.speciesUrl
)


fun PokemonBasicStatsResponse.mapToDetail(): PokemonStat = PokemonStat(
    baseStat, statName.name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
)

fun List<PokemonTypesResponse>.checkTypeList(size: Int, position: Int): String =
    if (this.size > size) {
        this[position].type.typeName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    } else ONE_TYPE_MONS

fun List<PokemonAbilitiesResponse>.checkAbilitiesList(size: Int, position: Int): String =
    if (this.size > size) {
        this[position].abilities.abilityName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    } else ONE_SKILL_MONS

fun PokemonSpeciesDetailResponse.mapToSpeciesDetail(): PokemonDetailSpecies = PokemonDetailSpecies(
    happines = pokemonHappines,
    captureRate = pokemonCaptureRate,
    color = pokemonColor.pokemonColor,
    eggGroup1 = pokemonEggGroup[0].eggName,
    eggGroup2 = pokemonEggGroup.checkEggGroupList(1, 1),
    generation = pokemonGeneration.pokemonGenerationLString,
    growthRate = pokemonGrowthRate.pokemonGrowthRate,
    habitat = pokemonHabitat.pokemonHabitat,
    shape = pokemonShape.pokemonShape
)

fun List<PokemonSpeciesEggGroupResponse>.checkEggGroupList(size: Int, position: Int): String =
    if (this.size > size) {
        this[position].eggName
    } else ONE_EGG_MONS

fun PokemonDetail.mapToFavoriteDatabase(): PokemonFavoriteEntity = PokemonFavoriteEntity(
    pokemonFavoriteId = null,
    pokemonWeight = pokemonWeight,
    pokemonHeight = pokemonHeight,
    pokemonName = pokemonName,
    pokemonImage = pokemonImage,
    pokemonSmallImage1 = pokemonSmallImage1,
    pokemonSmallImage2 = pokemonSmallImage2,
    pokemonSmallImage3 = pokemonSmallImage3,
    pokemonSmallImage4 = pokemonSmallImage4,
    pokemonStatName0 = pokemonStat0.name,
    pokemonStatName1 = pokemonStat1.name,
    pokemonStatName2 = pokemonStat2.name,
    pokemonStatName3 = pokemonStat3.name,
    pokemonStatName4 = pokemonStat4.name,
    pokemonStatName5 = pokemonStat5.name,
    pokemonStatPoint0 = pokemonStat0.point,
    pokemonStatPoint1 = pokemonStat1.point,
    pokemonStatPoint2 = pokemonStat2.point,
    pokemonStatPoint3 = pokemonStat3.point,
    pokemonStatPoint4 = pokemonStat4.point,
    pokemonStatPoint5 = pokemonStat5.point,
    pokemonType0 = pokemonType0,
    pokemonType1 = pokemonType1,
    pokemonAbility1 = pokemonAbility1,
    pokemonAbility2 = pokemonAbility2,
    pokemonSpeciesUrl = pokemonSpeciesUrl
)
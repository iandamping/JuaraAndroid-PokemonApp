package com.example.juaraandroid_pokemonapp.core.domain.common

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.*
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetailSpecies
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonStat
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
    pokemonType0 = pokemonTypes[0].type.typeName.replaceFirstChar { firstChar ->
        if (firstChar.isLowerCase()) firstChar.titlecase(
            Locale.getDefault()
        ) else firstChar.toString()
    },
    pokemonType1 = pokemonTypes.checkTypeList(1, 1),
    pokemonAbility1 = pokemonAbilities[0].abilities.abilityName.replaceFirstChar { firstChar ->
        if (firstChar.isLowerCase()) firstChar.titlecase(
            Locale.getDefault()
        ) else firstChar.toString()
    },
    pokemonAbility2 = pokemonAbilities.checkAbilitiesList(1, 1),
    pokemonSpeciesUrl = pokemonSpecies.speciesUrl
)

fun PokemonPaginationEntity.mapToDetail(): PokemonDetail = PokemonDetail(
    pokemonId = pokemonId,
    pokemonWeight = pokemonWeight,
    pokemonHeight = pokemonHeight,
    pokemonName = pokemonName,
    pokemonImage = pokemonImage,
    pokemonSmallImage1 = pokemonSmallImage1,
    pokemonSmallImage2 = pokemonSmallImage2,
    pokemonSmallImage3 = pokemonSmallImage3,
    pokemonSmallImage4 = pokemonSmallImage4,
    pokemonStat0 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat1 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat2 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat3 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat4 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat5 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonType0 = pokemonType0,
    pokemonType1 = pokemonType1 ?: "",
    pokemonAbility1 = pokemonAbility1,
    pokemonAbility2 = pokemonAbility2 ?: "",
    pokemonSpeciesUrl = pokemonSpeciesUrl
)

fun PokemonQuizEntity.mapToDetail(): PokemonDetail = PokemonDetail(
    pokemonId = pokemonId,
    pokemonWeight = pokemonWeight,
    pokemonHeight = pokemonHeight,
    pokemonName = pokemonName,
    pokemonImage = pokemonImage,
    pokemonSmallImage1 = pokemonSmallImage1,
    pokemonSmallImage2 = pokemonSmallImage2,
    pokemonSmallImage3 = pokemonSmallImage3,
    pokemonSmallImage4 = pokemonSmallImage4,
    pokemonStat0 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat1 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat2 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat3 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat4 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonStat5 = PokemonStat(pokemonStatPoint0, pokemonStatName0),
    pokemonType0 = pokemonType0,
    pokemonType1 = pokemonType1 ?: "",
    pokemonAbility1 = pokemonAbility1,
    pokemonAbility2 = pokemonAbility2 ?: "",
    pokemonSpeciesUrl = pokemonSpeciesUrl
)

fun List<PokemonQuizEntity>.mapListToDetail() = map { it.mapToDetail() }



fun PokemonBasicStatsResponse.mapToDetail(): PokemonStat = PokemonStat(
    baseStat, statName.name.replaceFirstChar { firstChar ->
        if (firstChar.isLowerCase()) firstChar.titlecase(
            Locale.getDefault()
        ) else firstChar.toString()
    }
)

fun List<PokemonTypesResponse>.checkTypeList(size: Int, position: Int): String =
    if (this.size > size) {
        this[position].type.typeName.replaceFirstChar { firstChar ->
            if (firstChar.isLowerCase()) firstChar.titlecase(
                Locale.getDefault()
            ) else firstChar.toString()
        }
    } else ONE_TYPE_MONS

fun List<PokemonAbilitiesResponse>.checkAbilitiesList(size: Int, position: Int): String =
    if (this.size > size) {
        this[position].abilities.abilityName.replaceFirstChar { firstChar ->
            if (firstChar.isLowerCase()) firstChar.titlecase(
                Locale.getDefault()
            ) else firstChar.toString()
        }
    } else ONE_SKILL_MONS

fun PokemonSpeciesDetailResponse.mapToSpeciesDetail(): PokemonDetailSpecies = PokemonDetailSpecies(
    happiness = pokemonHappiness,
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

fun PokemonDetail.mapToPaginationDatabase(): PokemonPaginationEntity = PokemonPaginationEntity(
    pokemonId = pokemonId,
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



fun PokemonDetail.mapToQuizDatabase(): PokemonQuizEntity = PokemonQuizEntity(
    pokemonId = pokemonId,
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

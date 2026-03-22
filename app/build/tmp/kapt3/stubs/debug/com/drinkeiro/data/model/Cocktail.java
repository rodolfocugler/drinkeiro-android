package com.drinkeiro.data.model;

import kotlinx.serialization.SerialName;
import kotlinx.serialization.Serializable;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\bI\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 u2\u00020\u0001:\u0002tuB\u00a7\u0002\b\u0011\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0001\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0014\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0016\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0017\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0019\u001a\u0004\u0018\u00010\u0005\u0012\u0010\b\u0001\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u00a2\u0006\u0002\u0010\u001fB\u00fd\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b\u00a2\u0006\u0002\u0010 J\t\u0010P\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010Q\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010S\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010U\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010V\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010W\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010X\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010Y\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010Z\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010[\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\\\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010]\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010^\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u00c6\u0003J\u000b\u0010_\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010`\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010a\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010b\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010c\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010e\u001a\u00020\u0005H\u00c6\u0003J\u008b\u0002\u0010f\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u00c6\u0001J\u0013\u0010g\u001a\u00020h2\b\u0010i\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010j\u001a\u00020\u0003H\u00d6\u0001J\t\u0010k\u001a\u00020\u0005H\u00d6\u0001J&\u0010l\u001a\u00020m2\u0006\u0010n\u001a\u00020\u00002\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020rH\u00c1\u0001\u00a2\u0006\u0002\bsR\u001e\u0010\u0019\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u001c\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b%\u0010\"\u001a\u0004\b&\u0010$R\u001c\u0010\f\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\'\u0010\"\u001a\u0004\b(\u0010$R\u001c\u0010\n\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b)\u0010\"\u001a\u0004\b*\u0010$R\u001e\u0010\u0018\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b+\u0010\"\u001a\u0004\b,\u0010$R\u001c\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b-\u0010\"\u001a\u0004\b.\u0010$R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b/\u0010\"\u001a\u0004\b0\u0010$R\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b1\u0010\"\u001a\u0004\b2\u0010$R\u001c\u0010\r\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b3\u0010\"\u001a\u0004\b4\u0010$R\u001e\u0010\u000b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b5\u0010\"\u001a\u0004\b6\u0010$R\u001e\u0010\u0017\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b7\u0010\"\u001a\u0004\b8\u0010$R\u001e\u0010\u0016\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b9\u0010\"\u001a\u0004\b:\u0010$R\"\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b;\u0010\"\u001a\u0004\b<\u0010=R\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b>\u0010\"\u001a\u0004\b?\u0010$R\u001e\u0010\u0010\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b@\u0010\"\u001a\u0004\bA\u0010$R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bB\u0010\"\u001a\u0004\bC\u0010$R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bD\u0010\"\u001a\u0004\bE\u0010$R\u001e\u0010\u0012\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bF\u0010\"\u001a\u0004\bG\u0010$R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bH\u0010\"\u001a\u0004\bI\u0010$R\u001e\u0010\u0014\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bJ\u0010\"\u001a\u0004\bK\u0010$R\u001e\u0010\b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bL\u0010\"\u001a\u0004\bM\u0010$R\u001e\u0010\t\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bN\u0010\"\u001a\u0004\bO\u0010$\u00a8\u0006v"}, d2 = {"Lcom/drinkeiro/data/model/Cocktail;", "", "seen1", "", "idDrink", "", "strDrink", "strDrinkAlternate", "strTags", "strVideo", "strCategory", "strIBA", "strAlcoholic", "strGlass", "strInstructions", "strInstructionsES", "strInstructionsDE", "strInstructionsFR", "strInstructionsIT", "strInstructionsZhHans", "strInstructionsZhHant", "strDrinkThumb", "strImageSource", "strImageAttribution", "strCreativeCommonsConfirmed", "dateModified", "strIngredient", "", "Lcom/drinkeiro/data/model/Ingredient;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getDateModified$annotations", "()V", "getDateModified", "()Ljava/lang/String;", "getIdDrink$annotations", "getIdDrink", "getStrAlcoholic$annotations", "getStrAlcoholic", "getStrCategory$annotations", "getStrCategory", "getStrCreativeCommonsConfirmed$annotations", "getStrCreativeCommonsConfirmed", "getStrDrink$annotations", "getStrDrink", "getStrDrinkAlternate$annotations", "getStrDrinkAlternate", "getStrDrinkThumb$annotations", "getStrDrinkThumb", "getStrGlass$annotations", "getStrGlass", "getStrIBA$annotations", "getStrIBA", "getStrImageAttribution$annotations", "getStrImageAttribution", "getStrImageSource$annotations", "getStrImageSource", "getStrIngredient$annotations", "getStrIngredient", "()Ljava/util/List;", "getStrInstructions$annotations", "getStrInstructions", "getStrInstructionsDE$annotations", "getStrInstructionsDE", "getStrInstructionsES$annotations", "getStrInstructionsES", "getStrInstructionsFR$annotations", "getStrInstructionsFR", "getStrInstructionsIT$annotations", "getStrInstructionsIT", "getStrInstructionsZhHans$annotations", "getStrInstructionsZhHans", "getStrInstructionsZhHant$annotations", "getStrInstructionsZhHant", "getStrTags$annotations", "getStrTags", "getStrVideo$annotations", "getStrVideo", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$app_debug", "$serializer", "Companion", "app_debug"})
public final class Cocktail {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String idDrink = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String strDrink = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strDrinkAlternate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strTags = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strVideo = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String strCategory = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strIBA = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String strAlcoholic = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String strGlass = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strInstructions = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strInstructionsES = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strInstructionsDE = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strInstructionsFR = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strInstructionsIT = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strInstructionsZhHans = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strInstructionsZhHant = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strDrinkThumb = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strImageSource = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strImageAttribution = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String strCreativeCommonsConfirmed = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String dateModified = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.drinkeiro.data.model.Ingredient> strIngredient = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.drinkeiro.data.model.Cocktail.Companion Companion = null;
    
    public Cocktail(@org.jetbrains.annotations.NotNull()
    java.lang.String idDrink, @org.jetbrains.annotations.NotNull()
    java.lang.String strDrink, @org.jetbrains.annotations.Nullable()
    java.lang.String strDrinkAlternate, @org.jetbrains.annotations.Nullable()
    java.lang.String strTags, @org.jetbrains.annotations.Nullable()
    java.lang.String strVideo, @org.jetbrains.annotations.NotNull()
    java.lang.String strCategory, @org.jetbrains.annotations.Nullable()
    java.lang.String strIBA, @org.jetbrains.annotations.NotNull()
    java.lang.String strAlcoholic, @org.jetbrains.annotations.NotNull()
    java.lang.String strGlass, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructions, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsES, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsDE, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsFR, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsIT, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsZhHans, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsZhHant, @org.jetbrains.annotations.Nullable()
    java.lang.String strDrinkThumb, @org.jetbrains.annotations.Nullable()
    java.lang.String strImageSource, @org.jetbrains.annotations.Nullable()
    java.lang.String strImageAttribution, @org.jetbrains.annotations.Nullable()
    java.lang.String strCreativeCommonsConfirmed, @org.jetbrains.annotations.Nullable()
    java.lang.String dateModified, @org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Ingredient> strIngredient) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIdDrink() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "idDrink")
    @java.lang.Deprecated()
    public static void getIdDrink$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStrDrink() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strDrink")
    @java.lang.Deprecated()
    public static void getStrDrink$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrDrinkAlternate() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strDrinkAlternate")
    @java.lang.Deprecated()
    public static void getStrDrinkAlternate$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrTags() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strTags")
    @java.lang.Deprecated()
    public static void getStrTags$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrVideo() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strVideo")
    @java.lang.Deprecated()
    public static void getStrVideo$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStrCategory() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strCategory")
    @java.lang.Deprecated()
    public static void getStrCategory$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrIBA() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strIBA")
    @java.lang.Deprecated()
    public static void getStrIBA$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStrAlcoholic() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strAlcoholic")
    @java.lang.Deprecated()
    public static void getStrAlcoholic$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStrGlass() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strGlass")
    @java.lang.Deprecated()
    public static void getStrGlass$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrInstructions() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strInstructions")
    @java.lang.Deprecated()
    public static void getStrInstructions$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrInstructionsES() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strInstructionsES")
    @java.lang.Deprecated()
    public static void getStrInstructionsES$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrInstructionsDE() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strInstructionsDE")
    @java.lang.Deprecated()
    public static void getStrInstructionsDE$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrInstructionsFR() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strInstructionsFR")
    @java.lang.Deprecated()
    public static void getStrInstructionsFR$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrInstructionsIT() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strInstructionsIT")
    @java.lang.Deprecated()
    public static void getStrInstructionsIT$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrInstructionsZhHans() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strInstructionsZH-HANS")
    @java.lang.Deprecated()
    public static void getStrInstructionsZhHans$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrInstructionsZhHant() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strInstructionsZH-HANT")
    @java.lang.Deprecated()
    public static void getStrInstructionsZhHant$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrDrinkThumb() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strDrinkThumb")
    @java.lang.Deprecated()
    public static void getStrDrinkThumb$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrImageSource() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strImageSource")
    @java.lang.Deprecated()
    public static void getStrImageSource$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrImageAttribution() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strImageAttribution")
    @java.lang.Deprecated()
    public static void getStrImageAttribution$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStrCreativeCommonsConfirmed() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strCreativeCommonsConfirmed")
    @java.lang.Deprecated()
    public static void getStrCreativeCommonsConfirmed$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDateModified() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "dateModified")
    @java.lang.Deprecated()
    public static void getDateModified$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Ingredient> getStrIngredient() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "strIngredient")
    @java.lang.Deprecated()
    public static void getStrIngredient$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.drinkeiro.data.model.Ingredient> component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.drinkeiro.data.model.Cocktail copy(@org.jetbrains.annotations.NotNull()
    java.lang.String idDrink, @org.jetbrains.annotations.NotNull()
    java.lang.String strDrink, @org.jetbrains.annotations.Nullable()
    java.lang.String strDrinkAlternate, @org.jetbrains.annotations.Nullable()
    java.lang.String strTags, @org.jetbrains.annotations.Nullable()
    java.lang.String strVideo, @org.jetbrains.annotations.NotNull()
    java.lang.String strCategory, @org.jetbrains.annotations.Nullable()
    java.lang.String strIBA, @org.jetbrains.annotations.NotNull()
    java.lang.String strAlcoholic, @org.jetbrains.annotations.NotNull()
    java.lang.String strGlass, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructions, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsES, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsDE, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsFR, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsIT, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsZhHans, @org.jetbrains.annotations.Nullable()
    java.lang.String strInstructionsZhHant, @org.jetbrains.annotations.Nullable()
    java.lang.String strDrinkThumb, @org.jetbrains.annotations.Nullable()
    java.lang.String strImageSource, @org.jetbrains.annotations.Nullable()
    java.lang.String strImageAttribution, @org.jetbrains.annotations.Nullable()
    java.lang.String strCreativeCommonsConfirmed, @org.jetbrains.annotations.Nullable()
    java.lang.String dateModified, @org.jetbrains.annotations.NotNull()
    java.util.List<com.drinkeiro.data.model.Ingredient> strIngredient) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.jvm.JvmStatic()
    public static final void write$Self$app_debug(@org.jetbrains.annotations.NotNull()
    com.drinkeiro.data.model.Cocktail self, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/drinkeiro/data/model/Cocktail.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/drinkeiro/data/model/Cocktail;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_debug"})
    @java.lang.Deprecated()
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.drinkeiro.data.model.Cocktail> {
        @org.jetbrains.annotations.NotNull()
        public static final com.drinkeiro.data.model.Cocktail.$serializer INSTANCE = null;
        
        private $serializer() {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] childSerializers() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.drinkeiro.data.model.Cocktail deserialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Decoder decoder) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
            return null;
        }
        
        @java.lang.Override()
        public void serialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull()
        com.drinkeiro.data.model.Cocktail value) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/drinkeiro/data/model/Cocktail$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/drinkeiro/data/model/Cocktail;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlinx.serialization.KSerializer<com.drinkeiro.data.model.Cocktail> serializer() {
            return null;
        }
    }
}
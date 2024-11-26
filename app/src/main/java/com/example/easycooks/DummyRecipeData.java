package com.example.easycooks;
import java.util.List;
import java.util.ArrayList;

import com.example.easycooks.R;
import com.example.easycooks.Recipe;

public class DummyRecipeData {
    public static List<Recipe> getDummyRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        
        recipeList.add(new Recipe.KoreanRecipe.Builder("김치찌개", 
                "김치 300g, 돼지고기 200g, 두부 1모, 대파 1대", 
                R.drawable.default_recipe_image)
            .spicyLevel("매움")
            .withKimchi()
            .build());

        recipeList.add(new Recipe.KoreanRecipe.Builder("순두부찌개",
                "두부 2모, 애호박 1/2개, 대파 1대, 고춧가루 2큰술",
                R.drawable.default_recipe_image)
            .spicyLevel("중간")
            .build());

        recipeList.add(new Recipe.DietRecipe.Builder("닭가슴살 샐러드", 
                "닭가슴살 200g, 양상추, 방울토마토", 
                R.drawable.default_recipe_image)
            .calories(300)
            .protein(25)
            .build());

        recipeList.add(new Recipe.DietRecipe.Builder("퀴노아 볼",
                "퀴노아 100g, 브로콜리 100g, 달걀 2개, 당근 1개",
                R.drawable.default_recipe_image)
            .calories(250)
            .protein(15)
            .build());

        recipeList.add(new Recipe.LowSaltRecipe.Builder("저염 된장찌개",
                "된장 2큰술, 두부 1모, 버섯 100g, 대파 1대, 다시마",
                R.drawable.default_recipe_image)
            .sodiumContent(200)
            .withSaltSubstitute("다시마")
            .build());
            
        recipeList.add(new Recipe.LowSaltRecipe.Builder("저염 닭찜",
                "닭가슴살 300g, 마늘, 생강, 레몬 1개, 허브",
                R.drawable.default_recipe_image)
            .sodiumContent(150)
            .withSaltSubstitute("레몬즙과 허브")
            .build());

        recipeList.add(new Recipe.VeganRecipe.Builder("비건 버거",
                "병아리콩 200g, 양파 1개, 버섯 100g, 귀리가루 50g",
                R.drawable.default_recipe_image)
            .addProteinSource("병아리콩")
            .addProteinSource("귀리")
            .withNutritionalYeast()
            .build());
            
        recipeList.add(new Recipe.VeganRecipe.Builder("로푸드 샐러드",
                "새싹채소 100g, 아보카도 1개, 견과류 50g, 올리브오일",
                R.drawable.default_recipe_image)
            .addProteinSource("견과류")
            .setRawVegan()
            .build());

        recipeList.add(new Recipe.LowSugarRecipe.Builder("저당 머핀",
                "아몬드가루 200g, 계란 2개, 스테비아 20g, 코코넛오일 30g",
                R.drawable.default_recipe_image)
            .sugarContent(3)
            .withSweetener("스테비아")
            .build());
            
        recipeList.add(new Recipe.LowSugarRecipe.Builder("과일 요거트 볼",
                "그릭요거트 200g, 사과 1/2개, 베리류 50g, 시나몬 약간",
                R.drawable.default_recipe_image)
            .sugarContent(8)
            .useFruitSweetness()
            .build());

        recipeList.add(Recipe.createDetailedRecipe(
            "불고기",
            "소고기 400g, 양파 1개, 당근 1개, 대파 1대, 간장 3큰술, 설탕 1큰술, 다진마늘 1큰술",
            "1. 소고기는 핏물을 제거하고 양념해주세요\n2. 채소들을 썰어주세요\n3. 팬에 고기와 채소를 넣고 볶아주세요",
            "40분",
            "4인분",
            R.drawable.default_recipe_image,
            "중급"
        ));

        recipeList.add(Recipe.createDetailedRecipe(
            "계란말이",
            "계란 4개, 대파 1/2대, 당근 1/4개, 소금 약간, 식용유",
            "1. 계란을 풀어 소금으로 간해주세요\n2. 채소는 잘게 썰어주세요\n3. 팬에 기름을 두르고 계란물을 부어 말아주세요",
            "15분",
            "2인분",
            R.drawable.default_recipe_image,
            "초급"
        ));
        return recipeList;
    }
} 
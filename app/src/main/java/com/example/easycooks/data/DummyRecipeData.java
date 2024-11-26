package com.example.easycooks.data;
import java.util.List;
import java.util.ArrayList;

import com.example.easycooks.R;
import com.example.easycooks.Recipe;

public class DummyRecipeData {
    public static List<Recipe> getDummyRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        
        recipeList.add(Recipe.createDetailedRecipe(
            "김치찌개",
            "김치 300g, 돼지고기 200g, 두부 1모, 대파 1대, 고춧가루 1큰술, 다진마늘 1큰술",
            "1. 김치를 적당한 크기로 썰어주세요\n2. 돼지고기를 넣고 볶아주세요\n3. 물을 넣고 끓여주세요\n4. 두부와 대파를 넣고 마무리해주세요",
            "30분",
            "2인분",
            R.drawable.default_recipe_image,
            "초급"
        ));

        recipeList.add(Recipe.createDetailedRecipe(
            "된장찌개",
            "된장 2큰술, 두부 1모, 애호박 1개, 양파 1/2개, 대파 1대, 다진마늘 1큰술",
            "1. 냄비에 물을 붓고 된장을 풀어주세요\n2. 채소들을 넣고 끓여주세요\n3. 두부를 넣고 마무리해주세요",
            "20분",
            "3인분",
            R.drawable.default_recipe_image,
            "초급"
        ));

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

        recipeList.add(Recipe.createDetailedRecipe(
            "비빔밥",
            "밥 2공기, 소고기 150g, 당근 1개, 오이 1개, 숙주나물 100g, 고추장 2큰술",
            "1. 각종 나물을 손질하고 볶아주세요\n2. 소고기는 간장으로 양념해 볶아주세요\n3. 밥 위에 나물과 고기를 올리고 고추장을 넣어 비벼주세요",
            "40분",
            "2인분",
            R.drawable.default_recipe_image,
            "중급"
        ));
        
        return recipeList;
    }
} 
class Recycler(
        val fontRecycler: FontRecyclerI,
        val extrasRecycler: ExtrasRecyclerI,
        val myCharacteresRecycler: MyCharacterRecycler
) : IPartRecyclerAdd<MyCharacter> {
    override fun addNew(entry: MyCharacter): MyCharacter {
        myCharacteresRecycler.lookFor(entry)?.let {
            return it
        }

        extrasRecycler.lookFor(entry.extras)?.let {
            return myCharacteresRecycler.addNew(MyCharacter(entry.representation, it))
        }

        fontRecycler.lookFor(entry.extras.font)?.let {
            return addNewCharacterFromFont(it, entry)
        }

        val newFont = fontRecycler.addNew(entry.extras.font)
        return addNewCharacterFromFont(newFont, entry)
    }

    private fun addNewCharacterFromFont(font: String, entry: MyCharacter): MyCharacter{
        val newExtras = extrasRecycler.addNew( Extras(entry.extras.size, font, entry.extras.color))
        return myCharacteresRecycler.addNew( MyCharacter(entry.representation, newExtras) )
    }
}

class FontRecyclerI(val fonts: ArrayList<String>) : PartRecycler<String>(fonts){
    override fun lookFor(entry: String): String? {
        return fonts.firstOrNull {
            it == entry
        }
    }
}

class ExtrasRecyclerI(val extras: ArrayList<Extras>) : PartRecycler<Extras>(extras) {
    override fun lookFor(entry: Extras): Extras? {
        return extras.firstOrNull {
            it.font == entry.font &&
                    it.size == entry.size &&
                    it.color.value == entry.color.value
        }
    }
}

class MyCharacterRecycler(val characters: ArrayList<MyCharacter>) : PartRecycler<MyCharacter>(characters){
    override fun lookFor(entry: MyCharacter): MyCharacter? {
        return characters.firstOrNull {
            it.representation == entry.representation &&
                    it.extras.font == entry.extras.font &&
                    it.extras.size == entry.extras.size &&
                    it.extras.color.value == entry.extras.color.value
        }
    }
}
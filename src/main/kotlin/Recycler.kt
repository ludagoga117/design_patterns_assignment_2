class Recycler(
        val myCharacteresRecycler: MyCharacterRecycler
) : IPartRecyclerAdd<MyCharacter> {
    override fun addNew(entry: MyCharacter): MyCharacter {
        return myCharacteresRecycler.lookFor(entry)
    }
}

class FontRecycler(val fonts: ArrayList<String>) : PartRecycler<String>(fonts){
    override fun lookFor(entry: String): String {
        fonts.firstOrNull {
            it == entry
        }?.let {
            return it
        }

        return this.addNew(entry)
    }
}

class ExtrasRecycler(val extras: ArrayList<Extras>, var fontRecycler: FontRecycler) : PartRecycler<Extras>(extras) {
    override fun lookFor(entry: Extras): Extras {
        extras.firstOrNull {
            it.font == entry.font &&
                    it.size == entry.size &&
                    it.color.value == entry.color.value
        }?.let {
            return it
        }

        return this.addNew(Extras(entry.size,fontRecycler.lookFor(entry.font),entry.color))
    }
}

class MyCharacterRecycler(val characters: ArrayList<MyCharacter>, var extrasRecycler: ExtrasRecycler) : PartRecycler<MyCharacter>(characters){

    override fun lookFor(entry: MyCharacter): MyCharacter {
        characters.firstOrNull {
            it.representation == entry.representation &&
                    it.extras.font == entry.extras.font &&
                    it.extras.size == entry.extras.size &&
                    it.extras.color.value == entry.extras.color.value
        }?.let {
            return it
        }

        return this.addNew(MyCharacter(entry.representation, extrasRecycler.lookFor(entry.extras)))
    }
}
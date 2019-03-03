class Recycler(
        val myCharacteresRecycler: MyCharacterRecycler
) : IPartRecyclerAdd<MyCharacter> {
    override fun addNew(entry: MyCharacter): MyCharacter {
        return myCharacteresRecycler.lookFor(entry)
    }
}

class FontRecycler(fonts: ArrayList<String>) : PartRecycler<String>(fonts){
    override fun lookFor(entry: String): String {
        entries.firstOrNull {
            it == entry
        }?.let {
            return it
        }

        return this.addNew(entry)
    }
}

class ExtrasRecycler(extras: ArrayList<Extras>, var fontRecycler:  PartRecycler<String>) : PartRecycler<Extras>(extras) {
    override fun lookFor(entry: Extras): Extras {
        entries.firstOrNull {
            it.font == entry.font &&
                    it.size == entry.size &&
                    it.color.value == entry.color.value
        }?.let {
            return it
        }

        return this.addNew(Extras(entry.size,fontRecycler.lookFor(entry.font),entry.color))
    }
}

class MyCharacterRecycler(characters: ArrayList<MyCharacter>, var extrasRecycler: PartRecycler<Extras>) : PartRecycler<MyCharacter>(characters){

    override fun lookFor(entry: MyCharacter): MyCharacter {
        entries.firstOrNull {
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
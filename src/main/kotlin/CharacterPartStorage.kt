interface IPartRecyclerLook<T> {
    fun lookFor(entry: T): T
}

interface IPartRecyclerAdd<T> {
    fun addNew(entry: T): T
}

abstract class PartRecycler<T>(var entries: ArrayList<T>) : IPartRecyclerLook<T>, IPartRecyclerAdd<T> {
    override fun addNew(entry: T): T {
        entries.add(entry)
        return entry
    }
}
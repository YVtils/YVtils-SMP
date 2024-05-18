@file:Suppress("removal")

package yv.tils.smp.mods.server.motd

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import org.bukkit.profile.PlayerTextures
import org.bukkit.profile.PlayerTextures.SkinModel
import java.net.URL
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * The code from Andre601 helped me very much for this: https://modrinth.com/plugin/advancedserverlist
 */
class FakePlayerProfile(name: String, uuid: UUID) : PlayerProfile {
    private val name: String = name
    private val uuid: UUID = uuid

    @Deprecated("Deprecated in Java", ReplaceWith("this.uuid()"))
    override fun getUniqueId(): UUID {
        return this.uuid()
    }

    override fun getName(): String {
        return this.name()
    }

    @Deprecated("Deprecated in Java", ReplaceWith("this.name()"))
    override fun setName(name: String?): String {
        return name!!
    }

    override fun getId(): UUID? {
        return null
    }

    @Deprecated("Deprecated in Java", ReplaceWith("this.uuid()"))
    override fun setId(uuid: UUID?): UUID? {
        return null
    }

    override fun getTextures(): PlayerTextures {
        return FakePlayeerTextures()
    }

    override fun setTextures(textures: PlayerTextures?) {
    }

    override fun getProperties(): Set<ProfileProperty> {
        return emptySet()
    }

    override fun hasProperty(property: String?): Boolean {
        return false
    }

    override fun setProperty(property: ProfileProperty) {
    }

    override fun setProperties(properties: Collection<ProfileProperty>) {
    }

    override fun removeProperty(property: String?): Boolean {
        return false
    }

    override fun clearProperties() {
    }

    override fun isComplete(): Boolean {
        return false
    }

    override fun completeFromCache(): Boolean {
        return false
    }

    override fun completeFromCache(onlineMode: Boolean): Boolean {
        return false
    }

    override fun completeFromCache(lookupUUID: Boolean, onlineMode: Boolean): Boolean {
        return false
    }

    override fun complete(textures: Boolean): Boolean {
        return false
    }

    override fun complete(textures: Boolean, onlineMode: Boolean): Boolean {
        return false
    }

    override fun update(): CompletableFuture<PlayerProfile> {
        return CompletableFuture.completedFuture(this)
    }

    override fun clone(): org.bukkit.profile.PlayerProfile {
        return this
    }

    override fun serialize(): Map<String, Any> {
        return emptyMap()
    }

    fun name(): String {
        return this.name
    }

    fun uuid(): UUID {
        return this.uuid
    }

    private class FakePlayeerTextures : PlayerTextures {
        override fun isEmpty(): Boolean {
            return false
        }

        override fun clear() {
        }

        override fun getSkin(): URL? {
            return null
        }

        override fun setSkin(skinUrl: URL?) {
        }

        override fun setSkin(skinUrl: URL?, skinModel: SkinModel?) {
        }

        override fun getSkinModel(): SkinModel {
            return SkinModel.CLASSIC
        }

        override fun getCape(): URL? {
            return null
        }

        override fun setCape(capeUrl: URL?) {
        }

        override fun getTimestamp(): Long {
            return 0L
        }

        override fun isSigned(): Boolean {
            return false
        }
    }

    companion object {
        fun create(name: String): FakePlayerProfile {
            return FakePlayerProfile(name, UUID.randomUUID())
        }
    }
}

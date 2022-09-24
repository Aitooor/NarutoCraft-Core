package es.narutocraft.narutocraftcore.data.configuration;

import de.exlll.configlib.annotation.Comment;
import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import de.exlll.configlib.format.FieldNameFormatters;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import lombok.Getter;

import java.io.File;

@Getter
public class MessagesFile extends BukkitYamlConfiguration {

    @Comment({"Use \\n for new lines", "Use %player% for player name" ,"" , "Join Message", "Use %player% for player name and prefix", "If prefix is empty, only player name will be used"})
    public String joinMessage =
            "\n&r" +
                    "\n&6&lNARUTOCRAFT &8| &bServer 1"+
                    "\n&r"+
                    "\n&aIP &7narutocraft.es"+
                    "\n&aTIENDA &7https://tienda.narutocraft.es"+
                    "\n&aDISCORD &7https://discord.com/invite/changeThis"+
                    "\n"+
                    "\n%player% &aesperamos que disfrutes" +
                    "\n&b¡Empecemos la aventura!" +
                    "\n&r" +
                    "\n&r";

    @Comment({"", "Common"})
    public String prefix = "&6&lNARUTOCRAFT &r";
    public String noPermission = "&cNo tienes permisos para hacer eso";
    public String playerNotFound = "&cEl jugador no existe";
    public String noOnlinePlayer = "&fEl jugador &b%player% &fno esta online";
    public String cooldown = "&cEspera &b%time% &cpara usar otro comando";
    public String reload = "&aConfig recargada";
    public String setSpawn = "&aSpawn establecido";
    public String setHoja = "&aSpawn de la Hoja establecido";
    public String setArena = "&aSpawn de la Arena establecido";
    public String setPiedra = "&aSpawn de la Piedra establecido";
    public String setNube = "&aSpawn de la Nube establecido";
    public String setNiebla = "&aSpawn de la Niebla establecido";
    public String helpCommand =
            "\n&r" +
                    "\n&6&lNARUTOCRAFT &8(&fAyuda&7)"+
                    "\n&r"+
                    "\n&7Añadir info de explicacione"+
                    "\n&r"+
                    "\n&7Mas info"+
                    "\n&r"+
                    "\n&7Mas info"+
                    "\n&r";

    @Comment({"", "Teleport"})
    public String tpSpawn = "&aTeletransportado al spawn";
    public String tpSpawnOther = "&fTeletransportado al jugador &b%player%";
    public String tpSpawnAll = "&fTeletransportado a todos los jugadores";
    public String tpDeny = "&cHas denegado la peticion de &b%player%";
    public String tpDenyTarget = "&cHas denegado la peticion de &b%player%";
    public String tpDenyNoRequest = "&cNo tienes peticiones pendientes";
    public String tpAccept = "&aHas aceptado la peticion de &b%player%";
    public String tpAcceptTarget = "&fEl jugador &b%player% &fha aceptado tu peticion";
    public String tpAcceptNoRequest = "&cNo tienes peticiones pendientes";
    public String tpaSender = "&fHas enviado solicitud de teletransporte a &b%player%";
    public String tpaTarget = "&fHas recibido una solicitud de teletransporte de &b%player%\n&7Usa &b/tpaccept &7para aceptarla\n&7o &b/tpdeny &7para denegarla\n";
    public String tpSelf = "&cNo puedes teletransportarte a ti mismo";
    public String tpAll = "&fTeletransportado a &b%player%";
    public String tpAllSelf = "&cNo puedes teletransportarte a ti mismo";
    public String tpAllSender = "&fHas teletransportado a todos hacia &b%player%";
    public String tpTop = "&aHas sido teletransportado hacia el top";
    public String tpTopFail = "&cNo hay ningun bloque mas arriba";
    public String tpPos = "&fHas sido teletransportado a la posicion &7X &b%x% &7Y &b%y% &7Z &b%z%";
    public String tpPosRealNumber = "&cLos datos deben ser numeros de cordenadas";
    public String tpPosWriteNumber = "&cEspecifica las cordenadas &7X, Y y Z";
    public String tpHereSender = "&fHas teletransportado hacia ti a &b%player%";
    public String tpHereTarget = "&fHas teletransportado a &b%player% &fhacia ti";

    @Comment({"", "Home"})
    public String homeNotFound = "&cLa casa no existe";
    public String homeNotSet = "&cNo has establecido una casa";
    public String homeSet = "&fHas establecido la casa &b%home%";
    public String maxHomes = "&cYa tienes el máximo de casas";
    public String homeExists = "&cYa tienes una casa con ese nombre";
    public String homeDeleted = "&fHas eliminado la casa &b%home%";
    public String homeNotExists = "&cNo tienes una casa con ese nombre";
    public String homeTeleported = "&fHas teletransportado a la casa &b%home%";

    @Comment({"", "Warps"})
    public String warpNotFound = "&cEl warp no existe";
    public String tpWarp = "&fTeletransportado al warp &b%warp%";
    public String alreadyExistWarp = "&cYa tienes un warp con el nombre &b%warp%";
    public String warpSet = "&fWarp &b%warp% &festablecido";
    public String warpRemoved = "&fWarp &c%warp% &fborrado";

    @Comment({"", "Gamemode"})
    public String cannotChangeGamemode = "&cNo puedes cambiar de gamemode";

    public String setSurvival = "&fHas cambiado tu modo de juego a &eSurvival";
    public String alreadySurvival = "&fYa tienes el modo de juego &eSurvival";
    public String alreadySurvivalTarget = "&fYa tienes el modo de juego &eSurvival";
    public String setSurvivalSender = "&fHas cambiado el modo de juego de &b%player% &fa &eSurvival";
    public String setSurvivalTarget = "&fTu modo de juego ha sido cambiado por &b%player% &fa &eSurvival";

    public String setCreative = "&fHas cambiado tu modo de juego a &eCreativo";
    public String alreadyCreative = "&fYa tienes el modo de juego &eCreativo";
    public String alreadyCreativeTarget = "&fYa tienes el modo de juego &eCreativo";
    public String setCreativeSender = "&fHas cambiado el modo de juego de &b%player% &fa &eCreativo";
    public String setCreativeTarget = "&fTu modo de juego ha sido cambiado por &b%player% &fa &eCreativo";

    public String setAdventure = "&fHas cambiado tu modo de juego a &eAventura";
    public String alreadyAdventure = "&fYa tienes el modo de juego &eAventura";
    public String alreadyAdventureTarget = "&fYa tienes el modo de juego &eAventura";
    public String setAdventureSender = "&fHas cambiado el modo de juego de &b%player% &fa &eAventura";
    public String setAdventureTarget = "&fTu modo de juego ha sido cambiado por &b%player% &fa &eAventura";

    public String setSpectator = "&fHas cambiado tu modo de juego a &eEspectador";
    public String alreadySpectator = "&fYa tienes el modo de juego &eEspectador";
    public String alreadySpectatorTarget = "&fYa tienes el modo de juego &eEspectador";
    public String setSpectatorSender = "&fHas cambiado el modo de juego de &b%player% &fa &eEspectador";
    public String setSpectatorTarget = "&fTu modo de juego ha sido cambiado por &b%player% &fa &eEspectador";

    @Comment({"", "Social Commands"})
    public String shopUrl = "https://tienda.narutocraft.es";
    public String twitterUrl = "https://twitter.com/changeThis";
    public String discordUrl = "https://discord.com/invite/changeThis";
    public String youtubeUrl = "https://www.youtube.com/c/changeThis";
    public String websiteUrl = "https://narutocraft.es";
    public String tiktokUrl = "https://www.tiktok.com/@changeThis";


    public MessagesFile() {
        super(
                new File(NarutoCraftCore.getInstance().getDataFolder(), "messages/messages.yml").toPath(),
                BukkitYamlProperties.builder().setFormatter(FieldNameFormatters.LOWER_UNDERSCORE).build()
        );
        this.loadAndSave();
    }
}

public class DAOFactory {
    private static IDAO dao = null;

    public static IDAO getDAOInstance(TypeDAO type) {
        if (type == TypeDAO.MySQL) {
            if (dao == null) {
                dao = MySQLDAO.getInstance();
            }
            return dao;
        }
        return null;
    }
}
const Navbar = () => {
  const isLoggedIn = localStorage.getItem('user');
  
  const handleLogout = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('userRole');
    window.location.href = '/';
  };

  return (
    <nav className="bg-gradient-to-r from-blue-900 to-blue-800 shadow-xl">
      <div className="container mx-auto px-6 py-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <a href="/" className="flex items-center space-x-2">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 text-orange-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
              <span className="text-2xl font-bold text-white">Fuel Management</span>
            </a>
          </div>

          <div className="hidden md:flex items-center space-x-8">
          <a href="/" className="text-gray-300 hover:text-white transition duration-300 no-underline font-semibold">Home</a>
          <a href="#about" className="text-gray-300 hover:text-white transition duration-300 no-underline font-semibold">About</a>
          <a href="#contact" className="text-gray-300 hover:text-white transition duration-300 no-underline font-semibold">Contact</a>
          <a href="#team" className="text-gray-300 hover:text-white transition duration-300 no-underline font-semibold">Team</a>
            
            {isLoggedIn ? (
              <div className="flex items-center space-x-4">
                <a href="/dashboard" className="no-underline bg-orange-500 hover:bg-orange-600 text-white font-bold py-2 px-4 rounded-lg transition duration-300">
                  Dashboard
                </a>
                <button 
                  onClick={handleLogout} 
                  className="no-underline bg-transparent hover:bg-blue-700 text-white font-semibold hover:text-orange-500 py-2 px-4 border border-orange-500 hover:border-transparent rounded-lg transition duration-300"
                >
                  Logout
                </button>
              </div>
            ) : (
              <div className="flex items-center space-x-4">
                <a href="/login" className="no-underline text-white hover:text-orange-500 transition duration-300">Login</a>
                <a href="/register" className=" no-underline bg-orange-500 hover:bg-orange-600 text-white font-bold py-2 px-4 rounded-lg transition duration-300">
                  Sign Up
                </a>
              </div>
            )}
          </div>

          <div className="md:hidden">
            <button className="text-white hover:text-orange-500">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
